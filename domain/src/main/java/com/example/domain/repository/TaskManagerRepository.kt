package com.example.domain.repository

import com.example.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskManagerRepository {

    suspend fun insertTask(taskModel: TaskModel)
    suspend fun getAllTasks(): Flow<List<TaskModel>>
    suspend fun getTaskByName(taskName: String): TaskModel?
    suspend fun updateTask(taskModel: TaskModel)
    suspend fun deleteTask(task: TaskModel)
    suspend fun getTask(id:Int): TaskModel?

}
