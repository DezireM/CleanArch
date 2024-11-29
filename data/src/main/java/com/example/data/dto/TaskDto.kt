package com.example.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.TaskModel

@Entity
data class TaskDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String? = null,
    val taskDate: String? = null,
    val taskImage: String? = null
)

fun TaskModel.toData() = TaskDto(
    id = id,
    taskName = taskName.takeIf { it.isNotEmpty() },
    taskDate = taskDate.takeIf { it.isNotEmpty() },
    taskImage = taskImage.takeIf { it.isNotEmpty() }
)

fun TaskDto.toDomain() = TaskModel(
    id = id,
    taskName = taskName.orEmpty(),
    taskDate = taskDate.orEmpty(),
    taskImage = taskImage.orEmpty()
)
