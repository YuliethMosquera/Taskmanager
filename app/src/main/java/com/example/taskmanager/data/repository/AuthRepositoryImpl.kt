package com.example.taskmanager.data.repository

import com.example.taskmanager.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun login(email: String, pass: String): Flow<Result<FirebaseUser>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener { result ->
                result.user?.let { trySend(Result.success(it)) }
            }
            .addOnFailureListener { trySend(Result.failure(it)) }
        awaitClose {}
    }

    override fun signUp(email: String, pass: String): Flow<Result<FirebaseUser>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { result ->
                result.user?.let { trySend(Result.success(it)) }
            }
            .addOnFailureListener { trySend(Result.failure(it)) }
        awaitClose {}
    }

    override fun logout() {
        auth.signOut()
    }
}