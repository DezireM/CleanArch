package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result
import java.time.LocalDateTime
import java.util.Calendar

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun insertTask(taskModel: TaskModel, versionSdk: Int): Result<TaskModel> {
        return try {
            val existingTask = taskManagerRepository.getTaskByName(taskModel.taskName)
            if (existingTask != null) {
                return Result.Failed("Task with the same name already exists.")
            }

            val taskDate = taskModel.taskDate.toIntOrNull()
            val currentHour: Int = if (versionSdk >= VERSION_CODES_0) {
                LocalDateTime.now().hour
            } else {
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            }

            if (taskDate == null || taskDate < currentHour) {
                return Result.Failed("Invalid task date. Task date must be in the future.")
            }

            taskManagerRepository.insertTask(taskModel)

            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed("Error occurred while inserting task: ${e.message}")
        }
    }
}

private const val VERSION_CODES_0 = 26
