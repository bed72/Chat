package com.bed.chat.presentation.shared.components.skeleton

import com.valentinilk.shimmer.shimmer

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun UserItemSkeleton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .shimmer(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(42.dp)
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .height(18.dp)
                .padding(horizontal = 8.dp)
                .weight(1f)
                .background(Color.Gray),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun UserItemPreview() {
    ChatTheme {
        UserItemSkeleton()
    }
}
