package com.bed.chat.presentation.shared.validator

interface Validator<T> {
    operator fun invoke(value: T): Pair<String?, T?>
}

interface FormValidator<S> {
    operator fun invoke(state: S): S
}
