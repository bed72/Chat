package com.bed.chat.presentation.feature.signup.state

import android.net.Uri

data class SignUpFormState(
    val picture: Uri? = null,

    val firstName: String = "",
    val firstNameMessage: String? = null,
    val firstNameIsValid: Boolean = false,

    val secondName: String = "",
    val secondNameMessage: String? = null,
    val secondNameIsValid: Boolean = false,

    val email: String = "",
    val emailMessage: String? = null,
    val emailIsValid: Boolean = false,

    val password: String = "",
    val passwordMessage: String? = null,
    val passwordIsValid: Boolean = false,

    val isLoading: Boolean = false,
    val isPictureSelectorBottomSheetOpen: Boolean = false
)
