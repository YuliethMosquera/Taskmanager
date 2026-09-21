package com.example.taskmanager.ui.screens.drafts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.local.entity.TaskDraftEntity
import com.example.taskmanager.domain.repository.DraftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftsViewModel @Inject constructor(
    private val draftRepository: DraftRepository
) : ViewModel() {

    val drafts: StateFlow<List<TaskDraftEntity>> = draftRepository.getDrafts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteDraft(id: Int) {
        viewModelScope.launch {
            draftRepository.deleteDraft(id)
        }
    }
}