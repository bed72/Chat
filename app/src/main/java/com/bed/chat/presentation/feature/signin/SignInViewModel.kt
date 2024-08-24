package com.bed.chat.presentation.feature.signin

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.presentation.feature.signin.state.SignInFormState
import com.bed.chat.presentation.feature.signin.state.SignInFormEvent

import com.bed.chat.presentation.shared.validator.Validator
import com.bed.chat.presentation.feature.signin.validators.EmailValidator
import com.bed.chat.presentation.feature.signin.validators.PasswordValidator

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val emailValidator: Validator<String> by lazy { EmailValidator() }
    private val passwordValidator: Validator<String> by lazy { PasswordValidator() }

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
        if (formState.emailIsValid && formState.passwordIsValid)
            formState = formState.copy(isLoading = true)
    }

    private fun emailChanged(value: String) {
        emailValidator(
            value = value,
            success = { email ->
                formState = formState.copy(email = email, emailMessage = null, emailIsValid = true)
            },
            failure = { message, email ->
                formState = formState.copy(email = email, emailMessage = message, emailIsValid = false)
            }
        )
    }

    private fun passwordChanged(value: String) {
        passwordValidator(
            value = value,
            success = { password ->
                formState = formState
                    .copy(password = password, passwordMessage = null, passwordIsValid = true)
            },
            failure = { message, password ->
                formState = formState
                    .copy(password = password, passwordMessage = message, passwordIsValid = false)
            }
        )
    }
}
