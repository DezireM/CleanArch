package com.geeks.cleanArch.domain.usecase

import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.repository.TaskManagerRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(private val taskManagerRepository: TaskManagerRepository) {
    suspend operator fun invoke(): Flow<List<TaskModel>> {
        return taskManagerRepository.getAllTasks()
    }
}