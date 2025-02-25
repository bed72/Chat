package com.bed.chat.presentation.feature.chats

import javax.inject.Inject
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.domain.repositories.ChatRepository
import com.bed.chat.domain.models.output.ChatOutputModel

import com.bed.chat.presentation.shared.extensions.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CheatUiState>(CheatUiState.Loading)
    val state = _state.asStateFlow()

    init {
        getChats()
    }

    fun getChats() {
        launch {
            _state.update { CheatUiState.Loading }

            repository(limit = 10, offset = 0).fold(
                onSuccess = { success -> _state.update { CheatUiState.Success(success) } },
                onFailure = { _state.update { CheatUiState.Failure } }
            )
        }
    }

    sealed interface CheatUiState {
        data object Loading : CheatUiState
        data object Failure : CheatUiState
        data class Success(val data: List<ChatOutputModel>) : CheatUiState
    }
}
