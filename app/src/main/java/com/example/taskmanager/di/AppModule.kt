package com.example.taskmanager.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Los proveedores de Room y Firebase se gestionan en DatabaseModule y FirebaseModule
}