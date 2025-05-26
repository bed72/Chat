package com.bed.chat.presentation.feature.message

import javax.inject.Inject

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlin.uuid.ExperimentalUuidApi

import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow

import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.paging.cachedIn
import androidx.paging.LoadState

import androidx.navigation.toRoute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import com.bed.chat.domain.repositories.UsersRepository
import com.bed.chat.domain.repositories.MessageRepository

import com.bed.chat.presentation.shared.routes.Routes
import com.bed.chat.presentation.shared.extensions.launch

import com.bed.chat.domain.models.output.UserOutputModel
import com.bed.chat.domain.models.input.MessageDataInputModel
import com.bed.chat.domain.models.output.MessageWithMembersOutputModel

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MessageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usersRepository: UsersRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {
    var message by mutableStateOf("")
        private set

    private val _isOnline = MutableStateFlow(false)
    val isOnline get() = _isOnline.asStateFlow()

    private val _showFailureState = Channel<Boolean>()
    val showFailureState = _showFailureState.receiveAsFlow()

    private val sendMessage = MutableSharedFlow<Unit>()
    private val parameter = savedStateHandle.toRoute<Routes.Message>()

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState
            .onStart { getUser() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UserState.Loading
            )

    private val _messagesState = MutableStateFlow<LoadState>(LoadState.Loading)
    val messages =
        messageRepository.getMessage(parameter.userId).cachedIn(viewModelScope)

    init {
        launch { sendMessage.mapLatest { sendMessage() }.collect {} }
        launch { combineFlowByFailure()  }
    }

    override fun onCleared() {
        super.onCleared()

        launch { messageRepository.disconnectWebSocket() }
    }

    fun onMessageChange(message: String) {
        this.message = message
    }

    fun onSendMessage() {
        launch { sendMessage.emit(Unit) }
    }

    fun setMessagesState(state: LoadState) {
        _messagesState.update { state }
    }

    fun dismissShowFailure() {
        launch { _showFailureState.send(false) }
    }

    fun onPause() {
        launch { messageRepository.disconnectWebSocket() }
    }

    fun onResume() {
        launch {
            messageRepository.connectWebSocket()
                .fold(
                    onSuccess = {
                        messageRepository.observerDataWebSocket()
                            .onEach { updateIsOnline(it) }
                            .launchIn(viewModelScope)
                    },
                    onFailure = {}
                )
        }
    }

    private fun updateIsOnline(data: MessageWithMembersOutputModel?) {
        data?.ids?.let { usersOnline ->
            _isOnline.update {usersOnline.contains(parameter.userId) }
        }
    }

    private suspend fun sendMessage() {
        if (message.isBlank()) return

        messageRepository.sendMessage(buildInput()).fold(
            onFailure = {},
            onSuccess = { message = "" }
        )
    }

    private suspend fun combineFlowByFailure() {
        combine(_userState ,_messagesState) { user, messages ->
            user to messages
        }.collect { (user, messages) ->
            _showFailureState.send(user is UserState.Failure || messages is LoadState.Error)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildInput() = MessageDataInputModel(
        text = message,
        receiverId = parameter.userId
    )

    private fun getUser() {
        _userState.update { UserState.Loading }

        launch {
            usersRepository.getUser(parameter.userId).fold(
                onSuccess = { success ->
                    _userState.update { UserState.Success(success) }
                },
                onFailure = { failure ->
                    _userState.update { UserState.Failure(failure.message ?: "Opps um erro ocorreu!!!") }
                },
            )
        }
    }

    sealed interface UserState {
        data object Loading : UserState
        data class Failure(val message: String) : UserState
        data class Success(val user: UserOutputModel) : UserState
    }
}
