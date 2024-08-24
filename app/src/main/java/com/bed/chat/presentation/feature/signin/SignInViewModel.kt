package com.bed.chat.presentation.feature.signin

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.presentation.feature.signin.state.SignInFormState
import com.bed.chat.presentation.feature.signin.state.SignInFormEvent


@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    var formState by mutableStateOf(SignInFormState())
        private set

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            SignInFormEvent.Submit -> { doSignIn() }
            is SignInFormEvent.EmailChanged -> { emailChanged(event.email) }
            is SignInFormEvent.PasswordChanged -> { passwordChanged(event.password) }
        }
    }

    private fun doSignIn() {
        formState = formState.copy(isLoading = true)
    }

    private fun emailChanged(email: String) {
        formState = formState.copy(email = email)
    }

    private fun passwordChanged(password: String) {
        formState = formState.copy(password = password)
    }
}
