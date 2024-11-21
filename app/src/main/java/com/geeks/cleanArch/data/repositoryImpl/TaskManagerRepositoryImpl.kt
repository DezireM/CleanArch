package com.geeks.cleanArch.data.repositoryImpl

import com.geeks.cleanArch.data.database.dao.TaskManagerDao
import com.geeks.cleanArch.data.dto.toData
import com.geeks.cleanArch.data.dto.toDomain
import com.geeks.cleanArch.domain.model.TaskModel
import com.geeks.cleanArch.domain.reposiotry.TaskManagerRepository

class TaskManagerRepositoryImpl(
    private val taskManagerDao: TaskManagerDao
) : TaskManagerRepository {

    override suspend fun insertTask(taskModel: TaskModel) {
        taskManagerDao.insertTask(taskModel.toData())
    }

    override suspend fun getAllTasks(): List<TaskModel> {
        return taskManagerDao.getAllTasks().map { it.toDomain()}
    }

    override suspend fun getTaskByName(taskName: String): TaskModel? {
        return taskManagerDao.getTaskByName(taskName)?.toDomain()
    }
}