package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(draft: TaskDraft)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun syncPendingTasks()
}