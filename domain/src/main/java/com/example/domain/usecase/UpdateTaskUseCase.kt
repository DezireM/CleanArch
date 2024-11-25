package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result


class UpdateTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun updateTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerRepository.updateTask(taskModel)
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed(e.message ?: "Error updating task")
        }
    }
}
