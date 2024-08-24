package com.bed.chat.domain.values

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

@JvmInline
value class PasswordValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$"

        operator fun invoke(value: String): EitherNel<String, PasswordValue> =
            if(isValid(value)) PasswordValue(value).right()
            else Values.INVALID_PASSWORD.message.leftNel()

        private fun isValid(value: String) = PATTERN.toRegex().matches(value)
    }
}
