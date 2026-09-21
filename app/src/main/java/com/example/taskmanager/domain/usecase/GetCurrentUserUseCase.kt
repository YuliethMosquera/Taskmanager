package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? = repository.currentUser
}