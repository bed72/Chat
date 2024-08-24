package com.bed.chat.domain.values

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

@JvmInline
value class EmailValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val INVALID = "E-mail inválido."
        private const val EMPTY = "O e-mail não pode ser vazio."

        private const val PATTERN = "^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+\\.[a-zA-z]{2,3}\$"

        operator fun invoke(value: String?): EitherNel<String, EmailValue> = when {
            value.isNullOrEmpty() -> EMPTY.leftNel()
            isValid(value) -> INVALID.leftNel()
            else -> EmailValue(value).right()
        }

        private fun isValid(value: String) = PATTERN.toRegex().matches(value).not()
    }
}
