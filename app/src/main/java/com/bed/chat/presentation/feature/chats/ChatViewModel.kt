package com.bed.chat.presentation.feature.chats

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.presentation.shared.extensions.launch

import com.bed.chat.domain.repositories.ChatRepository
import com.bed.chat.domain.models.output.ChatOutputModel
import com.bed.chat.domain.repositories.AuthenticationRepository

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    authenticationRepository: AuthenticationRepository
) : ViewModel() {

    val currentUser = authenticationRepository
        .currentUser
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(4_000)
        )

    private val _state = MutableStateFlow<ChatState>(ChatState.Loading)
    val state = _state
        .onStart { getChats() }
        .stateIn(
            scope = viewModelScope,
            initialValue = ChatState.Loading,
            started = SharingStarted.WhileSubscribed(4_000)
        )


    fun getChats(isRefresh: Boolean = false) {
        launch {
            if (isRefresh) _state.update { ChatState.Loading }

            chatRepository(limit = 10, offset = 0).fold(
                onSuccess = { success -> _state.update { ChatState.Success(success) } },
                onFailure = { _state.update { ChatState.Failure } }
            )
        }
    }

    sealed interface ChatState {
        data object Loading : ChatState
        data object Failure : ChatState
        data class Success(val data: List<ChatOutputModel>) : ChatState
    }
}
