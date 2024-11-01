package com.bed.chat.presentation.shared.validator

import com.bed.chat.domain.values.NameValue

class NameValidator : Validator<String> {
    override fun invoke(value: String): Pair<String?, String?> =
        NameValue(value).fold(
            { messages -> messages.firstOrNull() to null },
            { name -> null to name() },
        )
}
