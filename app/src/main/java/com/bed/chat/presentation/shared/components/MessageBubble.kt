package com.bed.chat.presentation.shared.components

import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.preview.fake.messageOneFake
import com.bed.chat.presentation.shared.preview.fake.messageFourFake
import com.bed.chat.presentation.shared.preview.fake.messageFiveFake
import com.bed.chat.presentation.shared.preview.fake.messageThreeFake

@Composable
fun MessageBubble(
    message: MessageOutputModel,
    previousMessage: MessageOutputModel?,
    modifier: Modifier = Modifier
) {
    val isCurrentMessage = message.isSelf
    val isSamePrevious = previousMessage?.senderId == message.senderId

    val surfaceColor = if  (isCurrentMessage) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.secondary

    val horizontalAlignment = if (isCurrentMessage) Alignment.End else Alignment.Start

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = horizontalAlignment
    ) {
        Surface(
            modifier = Modifier.wrapContentWidth(),
            color = surfaceColor.copy(alpha = 0.2f),
            shape =  RoundedCornerShape(100.dp),
        ) {
            Text(
                text = message.message,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (!isSamePrevious)
            Text(
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                text = message.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
    }
}

@Composable
@Preview(showBackground = true)
private fun MessageBubblePreview() {
    ChatTheme {
        MessageBubble(
            message = messageFiveFake,
            previousMessage = messageFourFake
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MessageBubbleSenderPreview() {
    ChatTheme {
        MessageBubble(
            message = messageOneFake,
            previousMessage = messageThreeFake
        )
    }
}
