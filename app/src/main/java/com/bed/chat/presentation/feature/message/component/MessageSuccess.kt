package com.bed.chat.presentation.feature.message.component

import kotlinx.coroutines.flow.Flow

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.MessageBubble
import com.bed.chat.presentation.shared.components.loader.LoadMore
import com.bed.chat.presentation.shared.components.loader.LoadFailure
import com.bed.chat.presentation.shared.components.empty.EmptyDefault
import com.bed.chat.presentation.shared.preview.provider.MessagePreviewParameterProvider

@Composable
fun MessageSuccess(
    messages: LazyPagingItems<MessageOutputModel>,
    modifier: Modifier = Modifier
) {
    if (messages.itemCount == 0) EmptyDefault(modifier = modifier)
    else
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            reverseLayout = true,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
        ) {
            items(messages.itemCount) { index ->
                val message = messages[index]
                val previousMessage = if (index > 0) messages[index - 1] else null

                message?.let {
                    MessageBubble(
                        message = it,
                        previousMessage = previousMessage
                    )
                }
            }

            if (messages.loadState.append is LoadState.Loading) item { LoadMore() }

            if (messages.loadState.append is LoadState.Error) item { LoadFailure { messages.retry() } }
        }
}

@Preview
@Composable
private fun MessageSuccessPreview(
    @PreviewParameter(MessagePreviewParameterProvider::class) messages: Flow<PagingData<MessageOutputModel>>
) {
    ChatTheme {
        MessageSuccess(
            messages = messages.collectAsLazyPagingItems()
        )
    }
}
