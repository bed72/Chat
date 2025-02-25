package com.bed.chat.presentation.feature.message.component

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.skeleton.MessageBubbleSkeleton

@Composable
fun MessageLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        repeat(6) {
            MessageBubbleSkeleton(it % 2 == 0)
        }
    }
}

@Preview
@Composable
private fun MessageLoadingPreview() {
    ChatTheme {
        MessageLoading()
    }
}
