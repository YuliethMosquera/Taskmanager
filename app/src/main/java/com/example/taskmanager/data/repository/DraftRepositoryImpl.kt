package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.dao.TaskDraftDao
import com.example.taskmanager.data.local.entity.TaskDraftEntity
import com.example.taskmanager.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DraftRepositoryImpl @Inject constructor(
    private val draftDao: TaskDraftDao
) : DraftRepository {

    override fun getDrafts(): Flow<List<TaskDraftEntity>> = draftDao.getAllDrafts()

    override suspend fun saveDraft(title: String, description: String) {
        draftDao.insertDraft(TaskDraftEntity(title = title, description = description))
    }

    override suspend fun deleteDraft(id: Int) {
        draftDao.deleteDraft(id)
    }
}