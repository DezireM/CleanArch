package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result

class GetTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(id: Int): Result<TaskModel> {
        return taskManagerRepository.getTask(id)
    }
}
