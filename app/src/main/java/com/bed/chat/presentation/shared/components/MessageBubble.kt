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

import com.bed.chat.domain.models.MessageOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.preview.fake.messageOneFake
import com.bed.chat.presentation.shared.preview.fake.messageFourFake
import com.bed.chat.presentation.shared.preview.fake.messageFiveFake
import com.bed.chat.presentation.shared.preview.fake.messageThreeFake

@Composable
fun ChatMessageBubble(
    message: MessageOutputModel,
    previousMessage: MessageOutputModel?,
    modifier: Modifier = Modifier
) {
    val isCurrentMessage = message.isSelf
    val isSamePrevious = previousMessage?.senderId == message.senderId

    val surfaceColor = if  (isCurrentMessage) MaterialTheme.colorScheme.tertiary
    else MaterialTheme.colorScheme.secondary

    val horizontalAlignment = if (isCurrentMessage) Alignment.End else Alignment.Start

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = horizontalAlignment
    ) {
        Surface(
            modifier = Modifier.wrapContentWidth(),
            color = surfaceColor,
            shape =  RoundedCornerShape(100.dp),
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (!isSamePrevious) {
            Text(
                text = message.date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ChatBubblePreview() {
    ChatTheme {
        ChatMessageBubble(
            message = messageFiveFake,
            previousMessage = messageFourFake
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChatBubbleSenderPreview() {
    ChatTheme {
        ChatMessageBubble(
            message = messageOneFake,
            previousMessage = messageThreeFake
        )
    }
}
