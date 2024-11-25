package com.geeks.cleanArch.presentation.fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeks.cleanArch.presentation.model.TaskUI
import com.example.domain.usecase.GetAllTasksUseCase
import com.example.domain.usecase.GetTaskUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.domain.usecase.TaskDelete
import com.example.domain.usecase.UpdateTaskUseCase
import com.geeks.cleanArch.presentation.model.toDomain
import com.geeks.cleanArch.presentation.model.toUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val getAllTaskUseCase: GetAllTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: TaskDelete,
    private val getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val tasksFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()

    private val _taskStateFlow = MutableStateFlow<TaskUI?>(null)
    val taskStateFlow: StateFlow<TaskUI?> = _taskStateFlow.asStateFlow()

    private val _insertMessageStateFlow = MutableStateFlow("")
    val insertMessageFlow: StateFlow<String> = _insertMessageStateFlow.asStateFlow()

    private val _updateMessageStateFlow = MutableStateFlow("")
    val updateMessageFlow: StateFlow<String> = _updateMessageStateFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow<LoadingState>(LoadingState.Loaded)
    val loadingFlow: StateFlow<LoadingState> = _loadingStateFlow.asStateFlow()

    private val _errorMessageFlow = MutableStateFlow<String?>(null)
    val errorMessageFlow: StateFlow<String?> = _errorMessageFlow.asStateFlow()

    private fun <T> runLaunchIO(block: suspend () -> T) {
        _loadingStateFlow.value = LoadingState.Loading
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { block() }
            } catch (e: Exception) {
                _errorMessageFlow.value = "Error: ${e.localizedMessage}"
            } finally {
                _loadingStateFlow.value = LoadingState.Loaded
            }
        }
    }

    fun loadTask(id: Int) {
        runLaunchIO {
            try {
                val task = getTaskUseCase(id)?.toUI()
                _taskStateFlow.value = task
            } catch (e: Exception) {
                _errorMessageFlow.value = "Error loading task: ${e.localizedMessage}"
            }
        }
    }

    fun loadTasks() {
        runLaunchIO {
            try {
                getAllTaskUseCase().collect { tasks ->
                    _tasksStateFlow.value = tasks.map { it.toUI() }
                }
            } catch (e: Exception) {
                _errorMessageFlow.value = "Error loading tasks: ${e.localizedMessage}"
            }
        }
    }

    fun updateTask(taskUI: TaskUI) {
        runLaunchIO {
            try {
                updateTaskUseCase.updateTask(taskUI.toDomain())
                _updateMessageStateFlow.value = "Task updated successfully"
            } catch (e: Exception) {
                _errorMessageFlow.value = "Error updating task: ${e.localizedMessage}"
            }
        }
    }

    fun deleteTask(taskUI: TaskUI) {
        runLaunchIO {
            try {
                deleteTaskUseCase.deleteTask(taskUI.toDomain())
                loadTasks()
            } catch (e: Exception) {
                _errorMessageFlow.value = "Error deleting task: ${e.localizedMessage}"
            }
        }
    }
}

sealed class LoadingState {
    object Loading : LoadingState()
    object Loaded : LoadingState()
    data class Error(val message: String) : LoadingState()
}
