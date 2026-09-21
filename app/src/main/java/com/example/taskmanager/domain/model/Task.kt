package com.example.taskmanager.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val updatedAt: Long
)