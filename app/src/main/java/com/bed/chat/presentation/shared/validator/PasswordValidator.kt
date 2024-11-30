package com.bed.chat.presentation.shared.validator

import com.bed.chat.domain.values.PasswordValue

class PasswordValidator : Validator<String> {
    override fun invoke(value: String): Pair<String?, String?> =
        PasswordValue(value).fold(
            { success -> null to success() },
            { failure -> failure.message to null }
        )
}
