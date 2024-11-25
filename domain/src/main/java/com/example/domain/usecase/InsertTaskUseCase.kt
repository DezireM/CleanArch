package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import java.time.LocalDateTime
import java.util.Calendar

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    private val VERSION_CODES_0 = 26

    suspend fun insertTask(taskModel: TaskModel, sdkInt: Int): String {
        val existingTask = taskManagerRepository.getTaskByName(taskModel.taskName)
        if (existingTask != null) {
            return "Already have that task"
        }

        val taskDate = taskModel.taskDate.toIntOrNull()

        val currentHour: Int = if (sdkInt >= VERSION_CODES_0) {
            LocalDateTime.now().hour
        } else {
            val calendar = Calendar.getInstance()
            calendar.get(Calendar.HOUR_OF_DAY)
        }

        if (taskDate == null || taskDate < currentHour) {
            return "Incorrect date"
        }

        taskManagerRepository.insertTask(taskModel)
        return "Task added successfully"
    }
}
