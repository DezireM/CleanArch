package com.geeks.cleanArch.domain.usecase

import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.reposiotry.TaskManagerRepository

class UseCase(private val taskManagerRepository: TaskManagerRepository) {
    suspend operator fun invoke(): List<TaskModel> {
        return taskManagerRepository.getAllTasks()
    }
}