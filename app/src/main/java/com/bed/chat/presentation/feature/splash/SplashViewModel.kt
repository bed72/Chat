package com.bed.chat.presentation.feature.splash

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.presentation.shared.extensions.launch

import com.bed.chat.domain.Constants
import com.bed.chat.domain.repositories.storage.StorageRepository
import com.bed.chat.domain.repositories.AuthenticationRepository

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    private val _state = MutableSharedFlow<AuthenticationState>()
    val state = _state.asSharedFlow()

    fun hasSession() { hasSavedToken() }

    private fun hasSavedToken() {
        launch { storageRepository.get(Constants.USER_TOKEN.value).collect { validateToken(it) } }
    }

    private suspend fun validateToken(token: String) {
        if (token.isEmpty()) {
            _state.emit(AuthenticationState.UserNotAuthenticated)

            return
        }

        authenticationRepository.validateToken(token).fold(
            onSuccess = { _state.emit(AuthenticationState.UserAuthenticated) },
            onFailure = { _state.emit(AuthenticationState.UserNotAuthenticated) }
        )
    }

    sealed interface AuthenticationState {
        data object UserAuthenticated : AuthenticationState
        data object UserNotAuthenticated : AuthenticationState
    }
}
