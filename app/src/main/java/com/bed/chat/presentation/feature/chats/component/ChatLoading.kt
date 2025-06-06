package com.bed.chat.presentation.feature.chats.component

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.skeleton.ChatItemSkeleton

@Composable
fun ChatLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        repeat(6) {
            ChatItemSkeleton()

            if (it < 6) HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Preview
@Composable
private fun ChatLoadingPreview() {
    ChatTheme {
        ChatLoading()
    }
}
