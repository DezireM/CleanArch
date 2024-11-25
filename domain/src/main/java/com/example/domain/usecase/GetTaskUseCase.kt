package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository

class GetTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(id: Int) : TaskModel? {
        return taskManagerRepository.getTask(id)
    }
}