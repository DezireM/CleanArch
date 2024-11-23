package com.geeks.cleanArch.data.repositoryImpl

import com.geeks.cleanArch.data.database.dao.TaskManagerDao
import com.geeks.cleanArch.data.dto.toData
import com.geeks.cleanArch.data.dto.toDomain
import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.repository.TaskManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskManagerRepositoryImpl(private val taskManagerDao: TaskManagerDao) :
    TaskManagerRepository {

    override suspend fun insertTask(taskModel: TaskModel) {
        taskManagerDao.insertTask(taskModel.toData())
    }

    override suspend fun getAllTasks(): Flow<List<TaskModel>> {
        return taskManagerDao.getAllTasks().map {list ->
            list.map {dto->
                dto.toDomain()
            }
        }
    }

    override suspend fun getTaskByName(taskName: String): TaskModel? {
        return taskManagerDao.getTaskByName(taskName)?.toDomain()
    }

    override suspend fun updateTask(taskModel: TaskModel) {
        taskManagerDao.updateTask(taskModel.toData())

    }

    override suspend fun deleteTask(task: TaskModel) {
        taskManagerDao.deleteTask(task.toData())
    }

    override suspend fun getTask(id: Int): TaskModel? {
        val taskDto = taskManagerDao.getTaskById(id)
        return taskDto?.toDomain()
    }


}