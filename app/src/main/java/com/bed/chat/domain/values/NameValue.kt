package com.bed.chat.domain.values

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

import kotlin.text.isNullOrEmpty

@JvmInline
value class NameValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val  MIN_LENGTH = 3
        private const val EMPTY = "O nome não pode ser vazio."
        private const val LENGTH = "O nome deve ter pelo menos 3 caracteres."

        operator fun invoke(value: String?): EitherNel<String, NameValue> = when {
            value.isNullOrEmpty() -> EMPTY.leftNel()
            value.length < MIN_LENGTH -> LENGTH.leftNel()
            else -> NameValue(value).right()
        }
    }
}
