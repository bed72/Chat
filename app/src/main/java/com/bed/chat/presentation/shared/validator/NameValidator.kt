package com.bed.chat.presentation.shared.validator

import com.bed.chat.domain.values.NameValue

class NameValidator : Validator<String> {
    override fun invoke(value: String): Pair<String?, String?> =
        NameValue(value).fold(
            { success -> null to success() },
            { failure -> failure.message to null },
        )
}
