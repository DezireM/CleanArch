package com.geeks.cleanArch.presentation.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeks.cleanArch.TaskUI
import com.geeks.cleanArch.domain.usecase.GetAllTasksUseCase
import com.geeks.cleanArch.domain.usecase.GetTaskUseCase
import com.geeks.cleanArch.domain.usecase.InsertTaskUseCase
import com.geeks.cleanArch.domain.usecase.TaskDelete
import com.geeks.cleanArch.domain.usecase.UpdateTaskUseCase
import com.geeks.cleanArch.presentation.fragment.base.BaseViewModel
import com.geeks.cleanArch.toDomain
import com.geeks.cleanArch.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val delete: TaskDelete,
    private val getTaskUseCase: GetTaskUseCase
): BaseViewModel() {

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val taskFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()



    fun fetchTask() {
        runLaunchIO {
            val task = getTaskUseCase(1)
            task?.let {
            }
        }
    }

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTasksUseCase()
                .collect { tasks ->
                    withContext(Dispatchers.Main) {
                        _tasksStateFlow.value = tasks.map { it.toUi() }
                    }
                }
        }
    }



    fun deleteTask(taskUI: TaskUI) {
        viewModelScope.launch {
            try {
                delete.deleteTask(taskUI.toDomain())
                loadTasks()
            } catch (e: Exception) {
                Log.e("Daisy", "Error : ${e.message}")
            }
        }
    }
}
