package com.geeks.cleanArch.domain.model

data class TaskModel(
    val id: Int,
    val taskName: String = String(),
    val taskDate: String = String(),
    val taskPhoto:String = String()
)
