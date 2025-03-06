package com.bed.chat.presentation.feature.message

import javax.inject.Inject

import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

import dagger.hilt.android.lifecycle.HiltViewModel

import androidx.paging.cachedIn

import androidx.navigation.toRoute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import com.bed.chat.presentation.shared.routes.Routes

import com.bed.chat.domain.repositories.MessageRepository
import com.bed.chat.presentation.shared.extensions.launch

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MessageViewModel @Inject constructor(
    private val repository: MessageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var message by  mutableStateOf("")
        private set
    private val sendMessage = MutableSharedFlow<Unit>()
    private val parameter = savedStateHandle.toRoute<Routes.Message>()

    val messages =
        repository.getMessage(parameter.userId).cachedIn(viewModelScope)

    init {
        launch {
            sendMessage.mapLatest { sendMessage() }.collect {}
        }
    }

    fun onMessageChange(message: String) {
        this.message = message
    }

    fun onSendMessage() {
        launch { sendMessage.emit(Unit) }
    }

    private suspend fun sendMessage() {
        repository.sendMessage(receiverId = parameter.userId, message = message)
    }
}
