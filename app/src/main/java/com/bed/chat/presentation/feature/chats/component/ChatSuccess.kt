package com.bed.chat.presentation.feature.chats.component

import androidx.compose.runtime.Composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import com.bed.chat.R

import com.bed.chat.domain.models.output.chat.ChatOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatItem
import com.bed.chat.presentation.feature.chats.ChatViewModel
import com.bed.chat.presentation.shared.components.EmptyContent
import com.bed.chat.presentation.shared.components.AnimatedContent
import com.bed.chat.presentation.shared.preview.provider.ChatsPreviewParameterProvider

@Composable
fun ChatSuccess(
    data: List<ChatOutputModel>,
    modifier: Modifier = Modifier,
) {
    when (data.isNotEmpty()) {
        false -> EmptyContent(
            modifier = modifier,
            message = R.string.common_generic_error_empty,
            resource = {
                AnimatedContent(
                    modifier = Modifier.size(200.dp),
                    resId = R.raw.animation_empty_list
                )
            }
        )
        true -> LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(data) { index, model ->
                ChatItem(model)

                if (index > data.lastIndex) return@itemsIndexed

                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
}

@Preview
@Composable
private fun ChatSuccessPreview(
    @PreviewParameter(ChatsPreviewParameterProvider ::class) chats: List<ChatOutputModel>
) {
    ChatTheme {
        ChatSuccess(
            data = ChatViewModel.CheatUiState.Success(chats).data,
        )
    }
}
