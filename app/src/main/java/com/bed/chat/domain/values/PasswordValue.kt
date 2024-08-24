package com.bed.chat.domain.values

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

@JvmInline
value class PasswordValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val EMPTY = "A senha não pode ser vazia."
        private const val LENGTH = "A senha deve ter no mínimo 8 caracteres."
        private const val NUMBER = "A senha deve conter pelo menos um número."
        private const val CAPITAL_LETTER = "A senha deve conter pelo menos uma letra maiúscula."

        private const val HAS_NUMBER = ".*[0-9].*"
        private const val HAS_MINIMAL_LENGTH = ".{8,}"
        private const val HAS_CAPITAL_LETTER = ".*[A-Z].*"

        operator fun invoke(value: String?): EitherNel<String, PasswordValue> = when {
            value.isNullOrEmpty() -> EMPTY.leftNel()
            hasCapitalLetter(value) -> CAPITAL_LETTER.leftNel()
            hasNumbers(value) -> NUMBER.leftNel()
            hasMinimalLength(value) -> LENGTH.leftNel()
            else -> PasswordValue(value).right()
        }

        private fun hasNumbers(value: String) = HAS_NUMBER.toRegex().matches(value).not()

        private fun hasCapitalLetter(value: String) = HAS_CAPITAL_LETTER.toRegex().matches(value).not()

        private fun hasMinimalLength(value: String) = HAS_MINIMAL_LENGTH.toRegex().matches(value).not()
    }
}
