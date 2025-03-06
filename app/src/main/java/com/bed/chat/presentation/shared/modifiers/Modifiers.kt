package com.bed.chat.presentation.shared.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

import androidx.compose.runtime.remember

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = Modifier.composed {
    clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}
