package com.geeks.cleanArch.domain.reposiotry

import com.geeks.cleanArch.domain.model.TaskModel

interface TaskManagerRepository {

    suspend fun insertTask(taskModel: TaskModel)
    suspend fun getAllTasks(): List<TaskModel>
    suspend fun getTaskByName(taskName: String): TaskModel?
}
