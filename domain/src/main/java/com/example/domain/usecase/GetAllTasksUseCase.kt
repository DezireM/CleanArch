package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(private val taskManagerRepository: TaskManagerRepository) {
    suspend operator fun invoke(): Flow<List<TaskModel>> {
        return taskManagerRepository.getAllTasks()
    }
}