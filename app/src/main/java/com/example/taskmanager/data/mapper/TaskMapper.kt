package com.example.taskmanager.data.mapper

import com.example.taskmanager.data.local.entity.TaskEntity
import com.example.taskmanager.data.remote.model.TaskDocument
import com.example.taskmanager.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        updatedAt = updatedAt
    )
}

fun Task.toEntity(syncStatus: String = "SYNCED"): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        syncStatus = syncStatus,
        updatedAt = updatedAt
    )
}

fun TaskDocument.toEntity(syncStatus: String = "SYNCED"): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        syncStatus = syncStatus,
        updatedAt = updatedAt
    )
}

fun TaskEntity.toDocument(): TaskDocument {
    return TaskDocument(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        updatedAt = updatedAt
    )
}