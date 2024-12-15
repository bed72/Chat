package com.bed.chat.presentation.feature.signin

import javax.inject.Inject

import com.bed.chat.presentation.shared.validator.Validator
import com.bed.chat.presentation.shared.validator.FormValidator
import com.bed.chat.presentation.shared.validator.NameValidator
import com.bed.chat.presentation.shared.validator.PasswordValidator

import com.bed.chat.presentation.feature.signin.state.SignInFormState

class SignInFormValidator @Inject constructor() : FormValidator<SignInFormState> {
    val usernameValidator: Validator<String> by lazy { NameValidator() }
    val passwordValidator: Validator<String> by lazy { PasswordValidator() }

    override operator fun invoke(state: SignInFormState): SignInFormState {
        val (usernameMessage, username) = usernameValidator(state.username)
        val (passwordMessage, password) = passwordValidator(state.password)

        return state.copy(
            usernameMessage = usernameMessage,
            username = username ?: state.username,
            usernameIsValid = usernameMessage == null,

            passwordMessage = passwordMessage,
            password = password ?: state.password,
            passwordIsValid = passwordMessage == null,

            formIsValid = usernameMessage == null && passwordMessage == null
        )
    }
}
