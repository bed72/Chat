package com.bed.chat.presentation.feature.signup.state

import android.net.Uri

sealed interface SignUpFormEvent {
    data object Submit : SignUpFormEvent
    data object OpenPictureSelectorBottomSheet : SignUpFormEvent
    data object ClosePictureSelectorBottomSheet : SignUpFormEvent
    data class EmailChanged(val email: String) : SignUpFormEvent
    data class PictureChanged(val picture: Uri?) : SignUpFormEvent
    data class NameChanged(val firstName: String) : SignUpFormEvent
    data class PasswordChanged(val password: String) : SignUpFormEvent
}
