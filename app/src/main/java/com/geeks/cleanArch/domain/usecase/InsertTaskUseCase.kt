package com.geeks.cleanArch.domain.usecase

import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.reposiotry.TaskManagerRepository
import java.time.LocalDateTime

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun insertTask(taskModel: TaskModel): String {
        val existingTask = taskManagerRepository.getTaskByName(taskModel.taskName)
        if (existingTask != null) {
            return "Task with the same name already exists."
        }

        val taskDate = taskModel.taskDate.toIntOrNull()
        val currentHour = LocalDateTime.now().hour

        if (taskDate == null || taskDate < currentHour) {
            return "Invalid task date. Task date must be in the future."
        }
        taskManagerRepository.insertTask(taskModel)
        return "Task added successfully"
    }
}