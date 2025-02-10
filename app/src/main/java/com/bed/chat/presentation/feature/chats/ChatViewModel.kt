package com.bed.chat.presentation.feature.chats

import javax.inject.Inject
import androidx.lifecycle.ViewModel

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

    private fun getChats() {
        launch {
            repository.getChats(limit = 10, offset = 0).fold(
                onSuccess = { _state.value = CheatUiState.Success(it) },
                onFailure = { _state.value = CheatUiState.Failure }
            )
        }
    }

    sealed interface CheatUiState {
        object Loading : CheatUiState
        object Failure : CheatUiState
        data class Success(val data: List<ChatOutputModel>) : CheatUiState
    }
}
