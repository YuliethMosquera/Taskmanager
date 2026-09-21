package com.example.taskmanager.domain.repository

import com.example.taskmanager.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    fun getDrafts(): Flow<List<TaskDraftEntity>>
    suspend fun saveDraft(title: String, description: String)
    suspend fun deleteDraft(id: Int)
}