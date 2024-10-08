package com.bed.chat.presentation.feature.signup

import android.net.Uri

import javax.inject.Inject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.presentation.shared.validator.Validator
import com.bed.chat.presentation.shared.validator.EmailValidator
import com.bed.chat.presentation.shared.validator.PasswordValidator

import com.bed.chat.presentation.feature.signup.state.SignUpFormEvent
import com.bed.chat.presentation.feature.signup.state.SignUpFormState

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val emailValidator: Validator<String> by lazy { EmailValidator() }
    private val passwordValidator: Validator<String> by lazy { PasswordValidator() }

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            SignUpFormEvent.Submit -> { doSignUp() }
            is SignUpFormEvent.EmailChanged -> { emailChanged(event.email) }
            is SignUpFormEvent.PictureChanged -> { pictureChanged(event.picture) }
            is SignUpFormEvent.PasswordChanged -> { passwordChanged(event.password) }
            is SignUpFormEvent.FirstNameChanged -> { firstNameChanged(event.firstName) }
            is SignUpFormEvent.SecondNameChanged -> { secondNameChanged(event.secondName) }
            SignUpFormEvent.OpenPictureSelectorBottomSheet -> { openPictureSelectorBottomSheet() }
            SignUpFormEvent.ClosePictureSelectorBottomSheet -> { closePictureSelectorBottomSheet() }
        }
    }

    private fun doSignUp() {
        with (formState) {
            if (firstNameIsValid && secondNameIsValid && emailIsValid && passwordIsValid)
                formState = formState.copy(isLoading = true)
        }
    }

    private fun pictureChanged(picture: Uri?) {
        formState = formState.copy(picture = picture)
    }

    private fun firstNameChanged(value: String) {
        formState = formState.copy(firstName = value)
    }

    private fun secondNameChanged(value: String) {
        formState = formState.copy(secondName = value)
    }

    private fun openPictureSelectorBottomSheet() {
        formState = formState.copy(isPictureSelectorBottomSheetOpen = true)
    }

    private fun closePictureSelectorBottomSheet() {
        formState = formState.copy(isPictureSelectorBottomSheetOpen = false)
    }

    private fun emailChanged(value: String) {
        emailValidator(
            value = value,
            default = { email ->
                formState = formState.copy(email = email, emailMessage = null, emailIsValid = false)
            },
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
            default = { password ->
                formState = formState.copy(password = password, passwordMessage = null, passwordIsValid = false)
            },
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
