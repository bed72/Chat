package com.bed.chat.presentation.feature.signin

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import com.bed.chat.presentation.feature.signin.state.SignInFormState
import com.bed.chat.presentation.feature.signin.state.SignInFormEvent

class SignInViewModel(
    private val validator: SignInFormValidator,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            SignInFormEvent.Submit -> { submit() }
            is SignInFormEvent.EmailChanged -> { emailChanged(event.email) }
            is SignInFormEvent.PasswordChanged -> { passwordChanged(event.password) }
        }
    }

    @Suppress("ForbiddenComment")
    private fun submit() {
        formState = formState.copy(isLoading = true, message = null)

        if (validateForm()) {
            // TODO sign in
        } else formState = formState.copy(isLoading = false, message = null)
    }

    private fun validateForm(): Boolean =
        validator(formState).also { formState = it }.formIsValid


    private fun emailChanged(value: String) {
        formState = formState.copy(
            email = value,
            emailMessage = if (value.isNotEmpty()) null else formState.emailMessage
        )
    }

    private fun passwordChanged(value: String) {
        formState = formState.copy(
            password = value,
            passwordMessage = if (value.isNotEmpty()) null else formState.passwordMessage
        )
    }
}
