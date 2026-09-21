package com.example.taskmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmanager.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {
    @Query("SELECT * FROM task_drafts ORDER BY createdAt DESC")
    fun getAllDrafts(): Flow<List<TaskDraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TaskDraftEntity)

    @Query("DELETE FROM task_drafts WHERE id = :id")
    suspend fun deleteDraft(id: Int)
}