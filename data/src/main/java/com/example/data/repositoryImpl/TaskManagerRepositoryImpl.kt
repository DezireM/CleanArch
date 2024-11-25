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
                Result.Failed("Data is empty")
            }
        } catch (ex: Exception) {
            Result.Failed(ex.localizedMessage ?: "Error fetching task")
        }
    }

    override suspend fun insertTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.insertTask(taskModel.toData())
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage ?: "Error inserting task")
        }
    }

    override suspend fun getAllTasks(): Flow<Result<List<TaskModel>>> {
        return taskManagerDao.getAllTasks()
            .map { list ->
                Result.Success(list.map { dto -> dto.toDomain() })
            }
            .catch { e ->
                emit(Result.Failed(e.localizedMessage ?: "Error fetching tasks"))
            }
    }

    override suspend fun getTaskByName(taskName: String): Result<TaskModel> {
        return try {
            val task = taskManagerDao.getTaskByName(taskName).toDomain()
            Result.Success(task)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage ?: "Error fetching task by name")
        }
    }

    override suspend fun updateTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.updateTask(taskModel.toData())
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage ?: "Error updating task")
        }
    }

    override suspend fun deleteTask(task: TaskModel): Result<TaskModel> {
        return try {
            taskManagerDao.deleteTask(task.toData())
            Result.Success(task)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage ?: "Error deleting task")
        }
    }
}
