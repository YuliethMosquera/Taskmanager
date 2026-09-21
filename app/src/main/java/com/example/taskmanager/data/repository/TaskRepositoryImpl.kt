package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.dao.TaskDao
import com.example.taskmanager.data.mapper.toDocument
import com.example.taskmanager.data.mapper.toDomain
import com.example.taskmanager.data.mapper.toEntity
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskDraft
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TaskRepository {

    private val userId: String
        get() = auth.currentUser?.uid ?: "anonymous"

    private fun getUserCollection() =
        firestore.collection("users").document(userId).collection("tasks")

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addTask(draft: TaskDraft) {
        val taskId = UUID.randomUUID().toString()
        val newTask = Task(
            id = taskId,
            title = draft.title,
            description = draft.description,
            isCompleted = false,
            updatedAt = System.currentTimeMillis()
        )


        val entity = newTask.toEntity(syncStatus = "PENDING_CREATE")
        taskDao.insertTask(entity)

        try {
            getUserCollection().document(taskId).set(entity.toDocument()).await()
            taskDao.insertTask(entity.copy(syncStatus = "SYNCED"))
        } catch (_: Exception) {

        }
    }

    override suspend fun updateTask(task: Task) {
        val entity = task.toEntity(syncStatus = "PENDING_UPDATE")
        taskDao.insertTask(entity)

        try {
            getUserCollection().document(task.id).set(entity.toDocument()).await()
            taskDao.insertTask(entity.copy(syncStatus = "SYNCED"))
        } catch (_: Exception) {

        }
    }

    override suspend fun deleteTask(taskId: String) {
        taskDao.deleteTaskById(taskId)

        try {
            getUserCollection().document(taskId).delete().await()
        } catch (_: Exception) {

        }
    }

    override suspend fun syncPendingTasks() {
        val pending = taskDao.getPendingTasks()
        for (task in pending) {
            try {
                when (task.syncStatus) {
                    "PENDING_CREATE", "PENDING_UPDATE" -> {
                        getUserCollection().document(task.id).set(task.toDocument()).await()
                        taskDao.insertTask(task.copy(syncStatus = "SYNCED"))
                    }
                    "PENDING_DELETE" -> {
                        getUserCollection().document(task.id).delete().await()
                        taskDao.deleteTaskById(task.id)
                    }
                }
            } catch (_: Exception) {

            }
        }
    }
}