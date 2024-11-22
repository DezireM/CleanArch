package com.geeks.cleanArch.domain.usecase

import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.repository.TaskManagerRepository

class UpdateTaskUseCase(private val taskRepository: TaskManagerRepository) {

    suspend fun updateTask(taskModel: TaskModel): String {
        return try {
            taskRepository.updateTask(taskModel)
            "Good"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}