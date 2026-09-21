package com.example.taskmanager.ui.screens.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.model.TaskDraft
import com.example.taskmanager.domain.repository.DraftRepository
import com.example.taskmanager.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TaskFormEvent {
    object Saved : TaskFormEvent
    data class Error(val message: String) : TaskFormEvent
}

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val draftRepository: DraftRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<TaskFormEvent>()
    val event: SharedFlow<TaskFormEvent> = _event.asSharedFlow()

    fun saveTask(title: String, description: String) {
        if (title.isBlank()) {
            viewModelScope.launch { _event.emit(TaskFormEvent.Error("El título no puede estar vacío")) }
            return
        }

        viewModelScope.launch {
            try {
                taskRepository.addTask(TaskDraft(title = title, description = description))
                _event.emit(TaskFormEvent.Saved)
            } catch (e: Exception) {
                _event.emit(TaskFormEvent.Error(e.message ?: "Error al guardar la tarea"))
            }
        }
    }

    fun saveAsDraft(title: String, description: String) {
        if (title.isBlank()) {
            viewModelScope.launch { _event.emit(TaskFormEvent.Error("El título no puede estar vacío")) }
            return
        }

        viewModelScope.launch {
            try {
                draftRepository.saveDraft(title, description)
                _event.emit(TaskFormEvent.Saved)
            } catch (e: Exception) {
                _event.emit(TaskFormEvent.Error(e.message ?: "Error al guardar el borrador"))
            }
        }
    }
}