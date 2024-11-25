package com.example.domain.usecase

import com.example.domain.result.Result
import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository

class TaskDelete(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun deleteTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerRepository.deleteTask(taskModel)

            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed(e.message ?: "Error deleting task")
        }
    }
}
