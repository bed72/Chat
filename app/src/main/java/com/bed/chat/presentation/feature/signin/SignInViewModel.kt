package com.bed.chat.presentation.feature.signin

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.bed.chat.domain.models.input.SignInInputModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.domain.repositories.AuthenticationRepository

import com.bed.chat.presentation.shared.extensions.launch
import com.bed.chat.presentation.feature.signin.state.SignInFormState
import com.bed.chat.presentation.feature.signin.state.SignInFormEvent

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validator: SignInFormValidator,
    private val repository: AuthenticationRepository,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            SignInFormEvent.Submit -> submit()
            is SignInFormEvent.UsernameChanged -> usernameChanged(event.username)
            is SignInFormEvent.PasswordChanged -> passwordChanged(event.password)
        }
    }

    private fun submit() {
        formState = formState.copy(isLoading = true, messageSuccess = null, messageFailure = null)

        if (validateForm()) {
            launch {
                repository.signIn(
                    SignInInputModel(
                        username = formState.username,
                        password = formState.password,
                    )
                ).fold(onSuccess = ::success, onFailure = ::failure)
            }
        } else formState = formState.copy(isLoading = false, messageFailure = null)
    }

    private fun failure(model: Throwable) {
        formState = formState.copy(
            isLoading = false,
            messageFailure = model.message,
        )
    }

    @Suppress("ForbiddenComment")
    private fun success(data: Unit) {
        formState = formState.copy(
            isLoading = false,
            messageSuccess = "Bem vindo!",
        )
    }

    private fun validateForm(): Boolean =
        validator(formState).also { formState = it }.formIsValid


    private fun usernameChanged(value: String) {
        formState = formState.copy(
            username = value,
            usernameMessage = if (value.isNotEmpty()) null else formState.usernameMessage
        )
    }

    private fun passwordChanged(value: String) {
        formState = formState.copy(
            password = value,
            passwordMessage = if (value.isNotEmpty()) null else formState.passwordMessage
        )
    }
}
