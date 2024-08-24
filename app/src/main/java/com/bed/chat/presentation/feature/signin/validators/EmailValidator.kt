package com.bed.chat.presentation.feature.signin.validators

import com.bed.chat.domain.values.EmailValue

import com.bed.chat.presentation.shared.validator.Validator

class EmailValidator : Validator<String> {
    override fun invoke(
        value: String,
        success: (String) -> Unit,
        failure: (String, String) -> Unit
    ) {
        EmailValue(value).fold(
            ifRight = { success(it()) },
            ifLeft = { failure(it.first(), value) }
        )
    }
}
