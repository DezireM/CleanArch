package com.example.domain.usecase

import com.example.domain.result.Result
import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository

class GetTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(id: Int): Result<TaskModel> {
        return try {
            val task = taskManagerRepository.getTask(id)
            Result.Success(task)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage ?: "Task not found")
        }
    }
}