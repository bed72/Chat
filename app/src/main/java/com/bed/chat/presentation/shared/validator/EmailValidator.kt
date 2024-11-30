package com.bed.chat.presentation.shared.validator

import com.bed.chat.domain.values.EmailValue

class EmailValidator : Validator<String> {
    override fun invoke(value: String): Pair<String?, String?> =
        EmailValue(value).fold(
            { success -> null to success() },
            { failure -> failure.message to null },
        )
}

