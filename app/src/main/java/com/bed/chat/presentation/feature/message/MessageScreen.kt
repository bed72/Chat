package com.bed.chat.presentation.feature.message

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import com.bed.chat.domain.models.MessageOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatScaffold

import com.bed.chat.presentation.shared.preview.fake.messageOneFake
import com.bed.chat.presentation.shared.preview.fake.messageTwoFake
import com.bed.chat.presentation.shared.preview.fake.messageFourFake
import com.bed.chat.presentation.shared.preview.fake.messageFiveFake
import com.bed.chat.presentation.shared.preview.fake.messageThreeFake
import com.bed.chat.presentation.feature.message.component.MessageTopBar
import com.bed.chat.presentation.feature.message.component.MessageContent
import com.bed.chat.presentation.shared.preview.provider.MessagePreviewParameterProvider

@Composable
fun MessageRoute(
    goBack: () -> Unit,
    viewModel: MessageViewModel = hiltViewModel()
) {
    val messages = flowOf(
        PagingData.from(
            listOf(
                messageOneFake,
                messageTwoFake,
                messageThreeFake,
                messageFourFake,
                messageFiveFake
            ),
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(true),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            )
        )
    ).collectAsLazyPagingItems()

    MessageScreen(
        message = "",
        goBack = goBack,
        onSendMessage = {},
        onMessageChange = {},
        messages = messages
    )
}

@Composable
fun MessageScreen(
    message: String,
    goBack: () -> Unit,
    onSendMessage: () -> Unit,
    onMessageChange: (String) -> Unit,
    messages: LazyPagingItems<MessageOutputModel>,
    modifier: Modifier = Modifier
) {
    ChatScaffold(
        modifier = modifier,
        topBar = { MessageTopBar(user = "Bed", lastSeen = "Online", goBack = goBack) },
        content = {
            MessageContent(
                message = message,
                messages = messages,
                onSendMessage = onSendMessage,
                onMessageChange = onMessageChange,
            )
        }
    )
}

@Preview
@Composable
private fun MessageScreenPreview(
    @PreviewParameter(MessagePreviewParameterProvider::class) messages: Flow<PagingData<MessageOutputModel>>
) {
    ChatTheme {
        MessageScreen(
            message = "Oie",
            goBack = {},
            onSendMessage = {},
            onMessageChange = {},
            messages = messages.collectAsLazyPagingItems()
        )
    }
}
