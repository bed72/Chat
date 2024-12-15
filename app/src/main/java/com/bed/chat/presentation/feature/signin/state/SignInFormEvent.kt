package com.bed.chat.presentation.feature.signin.state

sealed interface SignInFormEvent {
    data object Submit : SignInFormEvent
    data class PasswordChanged(val password: String) : SignInFormEvent
    data class UsernameChanged(val username: String) : SignInFormEvent
}
