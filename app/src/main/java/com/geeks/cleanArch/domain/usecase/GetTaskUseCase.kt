package com.geeks.cleanArch.domain.usecase

import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.repository.TaskManagerRepository

class GetTaskUseCase(
    private val taskManagerRepository: TaskManagerRepository
) {

    suspend operator fun invoke(id:Int): TaskModel?{
        return taskManagerRepository.getTask(id)
    }
}