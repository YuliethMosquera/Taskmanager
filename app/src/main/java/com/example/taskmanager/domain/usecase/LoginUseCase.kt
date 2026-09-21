package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, pass: String): Flow<Result<FirebaseUser>> {
        return repository.login(email, pass)
    }
}