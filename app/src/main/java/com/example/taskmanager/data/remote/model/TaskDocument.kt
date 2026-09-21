package com.example.taskmanager.data.remote.model

data class TaskDocument(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)