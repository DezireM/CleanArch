package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetAllTasksUseCase(private val taskManagerRepository: TaskManagerRepository) {
    suspend operator fun invoke(): Flow<Result<List<TaskModel>>> {
        return taskManagerRepository.getAllTasks()
            .map { tasks ->
                Result.Success(tasks)
            }
            .catch { e ->
                emit(Result.Failed(e.localizedMessage ?: "An error occurred"))
            }
    }
}
