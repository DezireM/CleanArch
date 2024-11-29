package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetAllTasksUseCase(private val taskManagerRepository: TaskManagerRepository) {
    suspend operator fun invoke(): Flow<Result<List<TaskModel>>> {
        return taskManagerRepository.getAllTasks()
            .catch { e ->
                emit(Result.Failed("Error getting all tasks: ${e.localizedMessage}"))
            }
    }
}