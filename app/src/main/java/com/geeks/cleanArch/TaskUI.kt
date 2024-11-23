package com.geeks.cleanArch

import com.geeks.cleanArch.domain.model.TaskModel

data class TaskUI(
    val id: Int,
    val taskName: String,
    val taskDate: String,
    val taskPhoto: String
)

fun TaskUI.toDomain() = TaskModel(id, taskName, taskDate,taskPhoto)
fun TaskModel.toUi() = TaskUI(id, taskName, taskDate,taskPhoto)