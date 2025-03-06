package com.bed.chat.presentation.feature.message.component

import kotlinx.coroutines.flow.Flow

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import com.bed.chat.R

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.MessageTextField
import com.bed.chat.presentation.shared.preview.provider.MessagePreviewParameterProvider

@Composable
fun MessageContent(
    message: String,
    onSendMessage: () -> Unit,
    onMessageChange: (String) -> Unit,
    messages: LazyPagingItems<MessageOutputModel>,
) {

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (messages.loadState.refresh) {
                LoadState.Loading -> MessageLoading()
                is LoadState.Error -> MessageFailure({ messages.refresh() })
                is LoadState.NotLoading -> MessageSuccess(messages)
            }
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
private fun MessageContentPreview(
    @PreviewParameter(MessagePreviewParameterProvider::class) messages: Flow<PagingData<MessageOutputModel>>
) {
    ChatTheme {
        MessageContent(
            message = "Olá, tudo bem?",
            onSendMessage = {},
            onMessageChange = {},
            messages = messages.collectAsLazyPagingItems()
        )
    }
}
