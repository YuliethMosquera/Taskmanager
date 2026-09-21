package com.example.taskmanager.di

import com.example.taskmanager.data.repository.AuthRepositoryImpl
import com.example.taskmanager.data.repository.DraftRepositoryImpl
import com.example.taskmanager.data.repository.TaskRepositoryImpl
import com.example.taskmanager.domain.repository.AuthRepository
import com.example.taskmanager.domain.repository.DraftRepository
import com.example.taskmanager.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDraftRepository(
        impl: DraftRepositoryImpl
    ): DraftRepository
}