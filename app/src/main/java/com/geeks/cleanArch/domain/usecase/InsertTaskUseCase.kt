package com.geeks.cleanArch.domain.usecase


import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.repository.TaskManagerRepository
import java.time.LocalDateTime

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {


   suspend fun  insertTask(taskModel: TaskModel): String {

        val existingTask = taskManagerRepository.getTaskByName(taskModel.taskName)
        if (existingTask != null) {
            return "Already have that"
        }


        val taskDate = taskModel.taskDate.toIntOrNull()
        val currentHour = LocalDateTime.now().hour

        if (taskDate == null || taskDate < currentHour) {
            return "Past time"
        }
        taskManagerRepository.insertTask(taskModel)
        return "Task added successfully"
    }


}