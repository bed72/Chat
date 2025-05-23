package com.bed.chat.domain.values

import com.bed.chat.domain.exceptions.ValidationException

@JvmInline
value class PasswordValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {

        private const val HAS_MIN_LENGTH = 6
        private const val HAS_MAX_LENGTH = 16
        private const val HAS_NUMBER = ".*[0-9].*"
        private const val HAS_CAPITAL_LETTER = ".*[A-Z].*"

        operator fun invoke(value: String?): Result<PasswordValue> = when {
            value.isNullOrEmpty() -> Result.failure(ValidationException.PasswordEmpty())
            hasMaxLength(value) -> Result.failure(ValidationException.PasswordMaxLength())
            hasNumbers(value) -> Result.failure(ValidationException.PasswordContainNumber())
            hasMinimalLength(value) -> Result.failure(ValidationException.PasswordMinLength())
            hasCapitalLetter(value) -> Result.failure(ValidationException.PasswordContainCapitalLetter())
            else -> Result.success(PasswordValue(value))
        }

        private fun hasMaxLength(value: String) = value.length > HAS_MAX_LENGTH

        private fun hasMinimalLength(value: String) = value.length < HAS_MIN_LENGTH

        private fun hasNumbers(value: String) = HAS_NUMBER.toRegex().matches(value).not()

        private fun hasCapitalLetter(value: String) = HAS_CAPITAL_LETTER.toRegex().matches(value).not()
    }
}
