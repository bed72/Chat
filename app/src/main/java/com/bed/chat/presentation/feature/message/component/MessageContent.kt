package com.bed.chat.presentation.feature.message.component

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.MessageTextField

@Composable
fun MessageContent(
    message: String,
    onSendMessage: () -> Unit,
    onMessageChange: (String) -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

        }

        MessageTextField(
            message = message,
            onSendMessage = onSendMessage,
            onMessageChange = onMessageChange,
            modifier = Modifier.padding(16.dp),
            placeholder = stringResource(R.string.message_text_field_placeholder_description)
        )
    }
}

@Preview
@Composable
private fun MessageContentPreview() {
    ChatTheme {
        MessageContent(
            message = "Olá, tudo bem?",
            onSendMessage = {},
            onMessageChange = {}
        )
    }
}
