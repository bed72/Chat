package com.bed.chat.domain.values

import kotlin.text.isNullOrEmpty

import com.bed.chat.domain.models.exception.ValidationExceptionModel

@JvmInline
value class NameValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val MIN_LENGTH = 2
        private const val MAX_LENGTH = 12
        private const val PATTERN = "[^a-zA-Z\\s]"

        operator fun invoke(value: String?): Result<NameValue> = when {
            value.isNullOrEmpty() -> Result.failure(ValidationExceptionModel.NameEmpty())
            isValid(value) -> Result.failure(ValidationExceptionModel.NameInvalid())
            value.length < MIN_LENGTH -> Result.failure(ValidationExceptionModel.NameMinLength())
            value.length > MAX_LENGTH -> Result.failure(ValidationExceptionModel.NameMaxLength())
            else -> Result.success(NameValue(value))
        }

        private fun isValid(value: String) = PATTERN.toRegex().matches(value)
    }
}
