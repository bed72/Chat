package com.bed.chat.presentation.shared.components.loader

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.CircularProgressIndicator

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun LoadMore(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun LoadMorePreview() {
    ChatTheme {
        LoadMore()
    }
}
