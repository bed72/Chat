package com.bed.chat.domain.values

import kotlin.text.isNullOrEmpty

import com.bed.chat.domain.exception.ValidationException

@JvmInline
value class NameValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val MIN_LENGTH = 2
        private const val MAX_LENGTH = 12
        private const val PATTERN = "[^a-zA-Z\\s]"

        operator fun invoke(value: String?): Result<NameValue> = when {
            value.isNullOrEmpty() -> Result.failure(ValidationException.NameEmpty())
            isValid(value) -> Result.failure(ValidationException.NameInvalid())
            value.length < MIN_LENGTH -> Result.failure(ValidationException.NameMinLength())
            value.length > MAX_LENGTH -> Result.failure(ValidationException.NameMaxLength())
            else -> Result.success(NameValue(value))
        }

        private fun isValid(value: String) = PATTERN.toRegex().matches(value)
    }
}
