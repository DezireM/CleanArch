package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result

class TaskDelete(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun deleteTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            (taskManagerRepository.deleteTask(taskModel))
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage ?: "Error deleting task")
        }
    }
}