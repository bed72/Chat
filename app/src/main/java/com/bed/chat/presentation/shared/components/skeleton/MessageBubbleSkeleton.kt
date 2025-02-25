package com.bed.chat.presentation.shared.components.skeleton

import com.valentinilk.shimmer.shimmer

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun MessageBubbleSkeleton(
    isSender: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().shimmer(),
        horizontalAlignment = if (isSender) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(27.dp))
                .height(54.dp)
                .fillMaxWidth(if (isSender) .72f else .82f)
                .background(Color.Gray),
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(48.dp)
                .height(32.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
@Preview(showBackground = true)
private fun MessageBubbleSkeletonPreview() {
    ChatTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            repeat(8) {
                MessageBubbleSkeleton(
                    isSender = it %2 == 0
                )
            }
        }
    }
}
