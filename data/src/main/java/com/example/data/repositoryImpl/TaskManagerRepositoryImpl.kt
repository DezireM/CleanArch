package com.example.data.repositoryImpl

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result
import com.example.data.database.dao.TaskManagerDao
import com.example.data.dto.toData
import com.example.data.dto.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskManagerRepositoryImpl(
    private val taskManagerDao: TaskManagerDao
) : TaskManagerRepository {

    override suspend fun getTask(id: Int): Result<TaskModel> {
        return try {
            val data = taskManagerDao.getTaskById(id)
            if (data != null) {
                Result.Success(data.toDomain())
            } else {
                Result.Failed("Task not found")
            }
        } catch (ex: Exception) {
            Result.Failed(ex.message.toString())
        }
    }

    override suspend fun insertTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.insertTask(taskModel.toData())
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed(e.message.toString())
        }
    }

    override suspend fun getAllTasks(): Flow<Result<List<TaskModel>>> {
        return taskManagerDao.getAllTasks()
            .map { list ->
                try {
                    Result.Success(list.map { it.toDomain() })
                } catch (e: Exception) {
                    Result.Failed(e.message ?: "An unknown error occurred")
                }
            }
    }

    override suspend fun getTaskByName(taskName: String): Result<TaskModel> {
        return try {
            val task = taskManagerDao.getTaskByName(taskName)?.toDomain()
            if (task != null) {
                Result.Success(task)
            } else {
                Result.Failed("Task not found")
            }
        } catch (e: Exception) {
            Result.Failed(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun updateTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.updateTask(taskModel.toData())
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed(e.message.toString())
        }
    }

    override suspend fun deleteTask(task: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.deleteTask(task.toData())
            Result.Success(task)
        } catch (e: Exception) {
            Result.Failed(e.message.toString())
        }
    }
}
