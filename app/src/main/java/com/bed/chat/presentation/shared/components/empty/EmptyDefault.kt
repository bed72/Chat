package com.bed.chat.presentation.shared.components.empty

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.size

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.AnimatedContent

@Composable
fun EmptyDefault(modifier: Modifier = Modifier) {
    EmptyContent(
        modifier = modifier,
        message = R.string.common_generic_error_empty,
        resource = {
            AnimatedContent(
                modifier = Modifier.size(200.dp),
                resId = R.raw.animation_empty_list
            )
        }
    )
}

@Preview
@Composable
private fun EmptyDefaultPreview() {
    ChatTheme {
        EmptyDefault()
    }
}
