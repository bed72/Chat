package com.bed.chat.presentation.feature.chats.component

import androidx.compose.runtime.Composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import com.bed.chat.domain.models.output.ChatOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatItem
import com.bed.chat.presentation.feature.chats.ChatViewModel
import com.bed.chat.presentation.shared.components.empty.EmptyDefault
import com.bed.chat.presentation.shared.components.NotificationInformation
import com.bed.chat.presentation.shared.preview.provider.ChatsPreviewParameterProvider

@Composable
fun ChatSuccess(
    data: List<ChatOutputModel>,
    shouldPermissionBanner: Boolean,
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit,
    onClick: (ChatOutputModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (data.isNotEmpty()) {
        false -> EmptyDefault(modifier = modifier)
        true -> LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            if (shouldPermissionBanner) item(key = "notification") {
                NotificationInformation(
                    modifier = Modifier.padding(top = 16.dp),
                    onDismiss = onDismiss,
                    onGoToSettings = onGoToSettings
                )
            }
            itemsIndexed(
                data,
                key = { _, model -> model.id }
            ) { index, model ->
                ChatItem(
                    model = model,
                    onClick = {
                        onClick(model)
                    }
                )

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
            data = ChatViewModel.ChatState.Success(chats).data,
            shouldPermissionBanner = true,
            onClick = {},
            onDismiss = {},
            onGoToSettings = {}
        )
    }
}
