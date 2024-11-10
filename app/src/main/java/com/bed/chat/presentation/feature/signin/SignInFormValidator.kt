package com.bed.chat.presentation.feature.signin

import javax.inject.Inject

import com.bed.chat.presentation.shared.validator.Validator
import com.bed.chat.presentation.shared.validator.FormValidator
import com.bed.chat.presentation.shared.validator.EmailValidator
import com.bed.chat.presentation.shared.validator.PasswordValidator

import com.bed.chat.presentation.feature.signin.state.SignInFormState

class SignInFormValidator @Inject constructor() : FormValidator<SignInFormState> {
    val emailValidator: Validator<String> by lazy { EmailValidator() }
    val passwordValidator: Validator<String> by lazy { PasswordValidator() }

    override operator fun invoke(state: SignInFormState): SignInFormState {
        val (emailMessage, email) = emailValidator(state.email)
        val (passwordMessage, password) = passwordValidator(state.password)

        return state.copy(
            emailMessage = emailMessage,
            email = email ?: state.email,
            emailIsValid = emailMessage == null,

            passwordMessage = passwordMessage,
            password = password ?: state.password,
            passwordIsValid = passwordMessage == null,

            formIsValid = emailMessage == null && passwordMessage == null
        )
    }
}
