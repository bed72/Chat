package com.bed.chat.domain.values

import com.bed.chat.domain.models.exception.ValidationExceptionModel

@JvmInline
value class EmailValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val PATTERN = "^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+\\.[a-zA-z]{2,3}\$"

        operator fun invoke(value: String?): Result<EmailValue> = when {
            value.isNullOrEmpty() -> Result.failure(ValidationExceptionModel.EmailEmpty())
            isValid(value) -> Result.failure(ValidationExceptionModel.EmailInvalid())
            else -> Result.success(EmailValue(value))
        }

        private fun isValid(value: String) = PATTERN.toRegex().matches(value).not()
    }
}
