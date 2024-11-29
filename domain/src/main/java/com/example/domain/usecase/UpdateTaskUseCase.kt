package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result

class UpdateTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(taskModel: TaskModel): Result<TaskModel> {
        return taskManagerRepository.updateTask(taskModel)
    }
}
