package com.bed.chat.presentation.feature.signup

import com.bed.chat.presentation.shared.validator.Validator
import com.bed.chat.presentation.shared.validator.FormValidator
import com.bed.chat.presentation.shared.validator.NameValidator
import com.bed.chat.presentation.shared.validator.EmailValidator
import com.bed.chat.presentation.shared.validator.PasswordValidator

import com.bed.chat.presentation.feature.signup.state.SignUpFormState

class SignUpFormValidator : FormValidator<SignUpFormState> {
    val nameValidator: Validator<String> by lazy { NameValidator() }
    val emailValidator: Validator<String> by lazy { EmailValidator() }
    val passwordValidator: Validator<String> by lazy { PasswordValidator() }

    override operator fun invoke(state: SignUpFormState): SignUpFormState {
        val (nameMessage, name) = nameValidator(state.name)
        val (emailMessage, email) = emailValidator(state.email)
        val (passwordMessage, password) = passwordValidator(state.password)

        return state.copy(
            nameMessage = nameMessage,
            name = name ?: state.name,
            nameIsValid = emailMessage == null,

            emailMessage = emailMessage,
            email = email ?: state.email,
            emailIsValid = emailMessage == null,

            passwordMessage = passwordMessage,
            password = password ?: state.password,
            passwordIsValid = passwordMessage == null,

            formIsValid = nameMessage == null && emailMessage == null && passwordMessage == null
        )
    }
}
