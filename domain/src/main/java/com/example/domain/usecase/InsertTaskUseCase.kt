package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(taskModel: TaskModel): Flow<Result<TaskModel>> {
        return flow {
            val result = taskManagerRepository.insertTask(taskModel)
            emit(result)
        }.catch { e ->
            emit(Result.Failed("Error inserting task: ${e.localizedMessage}"))
        }
    }
}
