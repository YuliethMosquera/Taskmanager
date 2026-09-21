package com.example.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.example.taskmanager.data.local.dao.TaskDao
import com.example.taskmanager.data.local.database.AppDatabase
import com.example.taskmanager.data.repository.TaskRepository
import com.example.taskmanager.data.repository.TaskRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_manager_db"
        ).build()
    }

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}

@Provides
@Singleton
fun provideTaskRepository(
    taskDao: TaskDao,
    firestore: FirebaseFirestore,
    auth: FirebaseAuth
): TaskRepository {
    return TaskRepositoryImpl(taskDao, firestore, auth)
}