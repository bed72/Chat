package com.bed.chat.presentation.feature.signin.state

sealed interface SignInFormEvent {
    data object Submit : SignInFormEvent
    data class EmailChanged(val email: String) : SignInFormEvent
    data class PasswordChanged(val password: String) : SignInFormEvent
}
