package com.example.taskmanager.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(email: String, pass: String, confirmPass: String) {
        if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
            _uiState.value = RegisterUiState.Error("Por favor completa todos los campos")
            return
        }

        if (pass != confirmPass) {
            _uiState.value = RegisterUiState.Error("Las contraseñas no coinciden")
            return
        }

        if (pass.length < 6) {
            _uiState.value = RegisterUiState.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            signUpUseCase(email, pass).collect { result ->
                result.fold(
                    onSuccess = { _uiState.value = RegisterUiState.Success },
                    onFailure = { _uiState.value = RegisterUiState.Error(it.message ?: "Error al registrar usuario") }
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }
}