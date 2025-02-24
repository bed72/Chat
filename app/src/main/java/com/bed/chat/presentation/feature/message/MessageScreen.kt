package com.bed.chat.presentation.feature.message

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatScaffold

import com.bed.chat.presentation.feature.message.component.MessageTopBar
import com.bed.chat.presentation.feature.message.component.MessageContent

@Composable
fun MessageRoute(
    goBack: () -> Unit,
    viewModel: MessageViewModel = hiltViewModel()
) {
    MessageScreen(
        message = "",
        goBack = goBack,
        onSendMessage = {},
        onMessageChange = {}
    )
}

@Composable
fun MessageScreen(
    message: String,
    goBack: () -> Unit,
    onSendMessage: () -> Unit,
    onMessageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ChatScaffold(
        modifier = modifier,
        topBar = { MessageTopBar(user = "Bed", lastSeen = "Online", goBack = goBack) },
        content = {
            MessageContent(
                message = message,
                onSendMessage = onSendMessage,
                onMessageChange = onMessageChange,
            )
        }
    )
}

@Preview
@Composable
private fun MessageScreenPreview() {
    ChatTheme {
        MessageScreen(
            message = "Oie",
            goBack = {},
            onSendMessage = {},
            onMessageChange = {}
        )
    }
}
