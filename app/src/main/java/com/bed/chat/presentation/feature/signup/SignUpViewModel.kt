package com.bed.chat.presentation.feature.signup

import android.net.Uri

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.models.output.FailureOutputModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.domain.repositories.AuthenticationRepository

import com.bed.chat.presentation.shared.extensions.launch
import com.bed.chat.presentation.feature.signup.state.SignUpFormEvent
import com.bed.chat.presentation.feature.signup.state.SignUpFormState

@HiltViewModel
class SignUpViewModel  @Inject constructor(
    private val validator: SignUpFormValidator,
    private val repository: AuthenticationRepository,
) : ViewModel() {

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            SignUpFormEvent.Submit -> { submit() }
            is SignUpFormEvent.EmailChanged -> emailChanged(event.email)
            is SignUpFormEvent.NameChanged -> nameChanged(event.firstName)
            is SignUpFormEvent.PictureChanged -> pictureChanged(event.picture)
            is SignUpFormEvent.PasswordChanged -> passwordChanged(event.password)
            SignUpFormEvent.OpenPictureSelectorBottomSheet -> openPictureSelectorBottomSheet()
            SignUpFormEvent.ClosePictureSelectorBottomSheet -> closePictureSelectorBottomSheet()
        }
    }

    private fun submit() {
        formState = formState.copy(isLoading = true, message = null)

        if (validateForm()) {
            launch {
                repository.signUp(
                    SignUpInputModel(
                        username = formState.name,
                        email = formState.email,
                        password = formState.password,
                        picture = null,
                    )
                )//.fold(::failure, ::success)
            }
        } else formState = formState.copy(isLoading = false, message = null)
    }

    @Suppress("UnusedPrivateMember")
    private fun failure(model: FailureOutputModel) {
        formState = formState.copy(
            isLoading = false,
            message = model.message,
        )
    }

    @Suppress("ForbiddenComment", "UnusedPrivateMember")
    private fun success(data: Unit) {
        // TODO: Navigate to login screen
    }

    private fun validateForm(): Boolean =
        validator(formState).also { formState = it }.formIsValid

    private fun pictureChanged(picture: Uri?) {
        formState = formState.copy(picture = picture)
    }

    private fun openPictureSelectorBottomSheet() {
        formState = formState.copy(isPictureSelectorBottomSheetOpen = true)
    }

    private fun closePictureSelectorBottomSheet() {
        formState = formState.copy(isPictureSelectorBottomSheetOpen = false)
    }

    private fun nameChanged(value: String) {
        formState = formState.copy(
            name = value,
            nameMessage = if (value.isNotEmpty()) null else formState.nameMessage
        )
    }

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
