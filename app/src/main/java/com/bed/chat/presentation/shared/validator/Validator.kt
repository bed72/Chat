package com.bed.chat.presentation.shared.validator

interface Validator<in T> {
    operator fun invoke(
        value: T,
        default: (String) -> Unit,
        success: (String) -> Unit,
        failure: (String, String) -> Unit
    )
}
