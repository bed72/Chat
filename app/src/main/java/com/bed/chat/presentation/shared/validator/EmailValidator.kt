package com.bed.chat.presentation.shared.validator

import com.bed.chat.domain.values.EmailValue

class EmailValidator : Validator<String> {
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

        EmailValue(value).fold(
            ifRight = { success(it()) },
            ifLeft = { failure(it.first(), value) }
        )
    }
}
