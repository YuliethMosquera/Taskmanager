package com.example.taskmanager.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    fun login(email: String, pass: String): Flow<Result<FirebaseUser>>
    fun signUp(email: String, pass: String): Flow<Result<FirebaseUser>>
    fun logout()
}