package com.bed.chat.presentation.feature.signup

import android.net.Uri

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel

import com.bed.chat.presentation.feature.signup.state.SignUpFormEvent
import com.bed.chat.presentation.feature.signup.state.SignUpFormState

class SignUpViewModel(
    private val validator: SignUpFormValidator,
) : ViewModel() {

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            SignUpFormEvent.Submit -> { submit() }
            is SignUpFormEvent.EmailChanged -> { emailChanged(event.email) }
            is SignUpFormEvent.NameChanged -> { nameChanged(event.firstName) }
            is SignUpFormEvent.PictureChanged -> { pictureChanged(event.picture) }
            is SignUpFormEvent.PasswordChanged -> { passwordChanged(event.password) }
            SignUpFormEvent.OpenPictureSelectorBottomSheet -> { openPictureSelectorBottomSheet() }
            SignUpFormEvent.ClosePictureSelectorBottomSheet -> { closePictureSelectorBottomSheet() }
        }
    }

    @Suppress("ForbiddenComment")
    private fun submit() {
        formState = formState.copy(isLoading = true, message = null)

        if (validateForm()) {
            // TODO: submit form
        } else formState = formState.copy(isLoading = false, message = null)
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
