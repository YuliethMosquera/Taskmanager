package com.example.taskmanager.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.value = LoginUiState.Error("Por favor completa todos los campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            loginUseCase(email, pass).collect { result ->
                result.fold(
                    onSuccess = { _uiState.value = LoginUiState.Success },
                    onFailure = { _uiState.value = LoginUiState.Error(it.message ?: "Error al iniciar sesión") }
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}