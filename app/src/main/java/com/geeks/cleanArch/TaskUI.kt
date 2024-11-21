package com.geeks.cleanArch

import com.geeks.cleanArch.domain.model.TaskModel

data class TaskUI(
    val id: Int,
    val taskName: String,
    val taskDate: String,
)

fun TaskUI.toDomain() = TaskModel(id, taskName, taskDate)
fun TaskModel.toUI() = TaskUI(id, taskName, taskDate)
