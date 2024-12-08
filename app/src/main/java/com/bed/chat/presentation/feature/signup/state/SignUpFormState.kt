package com.bed.chat.presentation.feature.signup.state

import android.net.Uri

data class SignUpFormState(
    val picture: Uri? = null,

    val name: String = "",
    val nameMessage: String? = null,
    val nameIsValid: Boolean = false,

    val email: String = "",
    val emailMessage: String? = null,
    val emailIsValid: Boolean = false,

    val password: String = "",
    val passwordMessage: String? = null,
    val passwordIsValid: Boolean = false,

    val message: String? = null,

    val isLoading: Boolean = false,
    val formIsValid: Boolean = false,
    val isCompressingImage: Boolean = false,
    val successfulRegistration: Boolean = false,
    val isPictureSelectorBottomSheetOpen: Boolean = false
)
