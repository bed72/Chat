package com.bed.chat.presentation.feature.chats

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.bed.chat.domain.models.output.chat.ChatOutputModel

import com.bed.chat.presentation.feature.chats.component.ChatTopBar
import com.bed.chat.presentation.feature.chats.component.ChatLoading
import com.bed.chat.presentation.feature.chats.component.ChatFailure
import com.bed.chat.presentation.feature.chats.component.ChatSuccess

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.presentation.shared.components.ChatScaffold

import com.bed.chat.presentation.shared.preview.provider.ChatsPreviewParameterProvider

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onTryAgainClick = { viewModel.getChats() }
    )
}

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatViewModel.CheatUiState,
    onTryAgainClick: () -> Unit,
) {
    ChatScaffold(
        modifier = modifier,
        topBar = { ChatTopBar(user = "Gabriel") },
        content = {
            when (state) {
                ChatViewModel.CheatUiState.Loading -> ChatLoading()
                ChatViewModel.CheatUiState.Failure -> ChatFailure(onTryAgainClick)
                is ChatViewModel.CheatUiState.Success -> ChatSuccess(state.data)
            }
        }
    )
}


@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
)
private fun ChatsScreenLoadingPreview() {
    ChatTheme {
        ChatScreen(
            state = ChatViewModel.CheatUiState.Loading,
            onTryAgainClick = {}
        )
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
)
private fun ChatsScreenFailurePreview() {
    ChatTheme {
        ChatScreen(
            state = ChatViewModel.CheatUiState.Failure,
            onTryAgainClick = {}
        )
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
)
private fun ChatsScreenSuccessPreview(
    @PreviewParameter(ChatsPreviewParameterProvider ::class) chats: List<ChatOutputModel>
) {
    ChatTheme {
        ChatScreen(
            state = ChatViewModel.CheatUiState.Success(chats),
            onTryAgainClick = {}
        )
    }
}
