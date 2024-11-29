import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllTasksUseCase
import com.example.domain.usecase.GetTaskUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.domain.usecase.TaskDelete
import com.example.domain.usecase.UpdateTaskUseCase
import com.geeks.cleanArch.presentation.model.TaskUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.domain.result.Result
import com.geeks.cleanArch.presentation.model.toDomain
import com.geeks.cleanArch.presentation.model.toUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class TasksViewModel(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val taskDeleteUseCase: TaskDelete,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val insertTaskUseCase: InsertTaskUseCase
) : ViewModel() {

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val tasksFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()

    private val _taskStateFlow = MutableStateFlow<Result<TaskUI>>(Result.Loading)
    val taskStateFlow: StateFlow<Result<TaskUI>> = _taskStateFlow.asStateFlow()

    private val _insertMessageStateFlow = MutableStateFlow(String())
    val insertMessageFlow: StateFlow<String> = _insertMessageStateFlow.asStateFlow()

    private val _updateMessageStateFlow = MutableStateFlow(String())
    val updateMessageFlow: StateFlow<String> = _updateMessageStateFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow<LoadingState>(LoadingState.Loaded)
    val loadingFlow: StateFlow<LoadingState> = _loadingStateFlow.asStateFlow()

    private val _errorMessageFlow = MutableStateFlow(String())
    val errorMessageFlow: StateFlow<String> = _errorMessageFlow.asStateFlow()

    private fun <T> runLaunchIO(block: suspend CoroutineScope.() -> T) {
        viewModelScope.launch {
            _loadingStateFlow.value = LoadingState.Loading
            try {
                withContext(Dispatchers.IO) { block() }
            } catch (e: Exception) {
                _errorMessageFlow.value = "Error: ${e.localizedMessage}"
            } finally {
                _loadingStateFlow.value = LoadingState.Loaded
            }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            getAllTasksUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> _tasksStateFlow.value = emptyList()
                    is Result.Success -> _tasksStateFlow.value = result.data.map { it.toUI() }
                    is Result.Failed -> _errorMessageFlow.value = result.message
                }
            }
        }
    }

    fun getTask(id: Int) {
        runLaunchIO {
            when (val result = getTaskUseCase(id)) {
                is Result.Loading -> _taskStateFlow.value = Result.Loading
                is Result.Success -> _taskStateFlow.value = Result.Success(result.data.toUI())
                is Result.Failed -> _taskStateFlow.value = Result.Failed(result.message)
            }
        }
    }

    fun insertTask(taskUI: TaskUI, versionSdk: Int) {
        runLaunchIO {
            insertTaskUseCase(taskUI.toDomain()).collect { result ->
                when (result) {
                    is Result.Loading -> _taskStateFlow.value = Result.Loading
                    is Result.Success -> {
                        _taskStateFlow.value = Result.Success(result.data.toUI())
                        loadTasks()
                    }
                    is Result.Failed -> {
                        _taskStateFlow.value = Result.Failed(result.message )
                    }
                }
            }   
        }
    }


    fun updateTask(taskUI: TaskUI) {
        runLaunchIO {
            when (val result = updateTaskUseCase.updateTask(taskUI.toDomain())) {
                is Result.Loading -> _taskStateFlow.value = Result.Loading
                is Result.Success -> {
                    _taskStateFlow.value = Result.Success(result.data.toUI())
                    loadTasks()
                }
                is Result.Failed -> _taskStateFlow.value = Result.Failed(result.message)
            }
        }
    }

    fun deleteTask(taskUI: TaskUI) {
        runLaunchIO {
            when (val result = taskDeleteUseCase.deleteTask(taskUI.toDomain())) {
                is Result.Loading -> _taskStateFlow.value = Result.Loading
                is Result.Success -> {
                    _taskStateFlow.value = Result.Success(result.data.toUI())
                    loadTasks()
                }
                is Result.Failed -> _taskStateFlow.value = Result.Failed(result.message)
            }
        }
    }

    sealed class LoadingState {
        object Loading : LoadingState()
        object Loaded : LoadingState()
        data class Failed(val message: String) : LoadingState()
    }
}
