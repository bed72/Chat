package com.bed.chat.presentation.feature.signin.validators

import com.bed.chat.domain.values.PasswordValue

import com.bed.chat.presentation.shared.validator.Validator

class PasswordValidator : Validator<String> {
    override fun invoke(
        value: String,
        success: (String) -> Unit,
        failure: (String, String) -> Unit
    ) {
        PasswordValue(value).fold(
            ifRight = { success(it()) },
            ifLeft = { failure(it.first(), value) }
        )
    }
}
