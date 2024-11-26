package com.example.data.repositoryImpl

import com.example.data.database.dao.TaskManagerDao
import com.example.data.dto.toData
import com.example.data.dto.toDomain
import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch


class TaskManagerRepositoryImpl(
    private val taskManagerDao: TaskManagerDao
) : TaskManagerRepository {

    override suspend fun getTask(id: Int): Result<TaskModel> {
        return try {
            val data = taskManagerDao.getTaskById(id)
            if (data != null) {
                Result.Success(data.toDomain())
            } else {
                Result.Failed("No task found with id: $id")
            }
        } catch (ex: Exception) {
            Result.Failed("Error fetching task: ${ex.localizedMessage}")
        }
    }

    override suspend fun insertTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.insertTask(taskModel.toData())
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed("Error inserting task: ${e.localizedMessage}")
        }
    }

    override suspend fun getAllTasks(): Flow<Result<List<TaskModel>>> {
        return taskManagerDao.getAllTasks()
            .map { list ->
                Result.Success(list.map { it.toDomain() }) // DTO to Domain
            }
            .catch { e ->
                emit(Result.Failed("Error fetching tasks: ${e.localizedMessage}"))
            }
    }

    override suspend fun getTaskByName(taskName: String): Result<TaskModel> {
        return try {
            val task = taskManagerDao.getTaskByName(taskName)?.toDomain()
            if (task != null) {
                Result.Success(task)
            } else {
                Result.Failed("Task with name '$taskName' not found")
            }
        } catch (e: Exception) {
            Result.Failed("Error fetching task by name: ${e.localizedMessage}")
        }
    }

    override suspend fun updateTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.updateTask(taskModel.toData()) // Domain to DTO
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed("Error updating task: ${e.localizedMessage}")
        }
    }

    override suspend fun deleteTask(task: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.deleteTask(task.toData()) // Domain to DTO
            Result.Success(task)
        } catch (e: Exception) {
            Result.Failed("Error deleting task: ${e.localizedMessage}")
        }
    }
}
