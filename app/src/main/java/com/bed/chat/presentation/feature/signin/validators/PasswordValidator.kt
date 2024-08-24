package com.bed.chat.presentation.feature.signin.validators

import com.bed.chat.domain.values.PasswordValue

import com.bed.chat.presentation.shared.validator.Validator

class PasswordValidator : Validator<String> {
    override fun invoke(
        value: String,
        default: (String) -> Unit,
        success: (String) -> Unit,
        failure: (String, String) -> Unit
    ) {
        if (value.isEmpty()) {
            default(value)

            return
        }

        PasswordValue(value).fold(
            ifRight = { success(it()) },
            ifLeft = { failure(it.first(), value) }
        )
    }
}
