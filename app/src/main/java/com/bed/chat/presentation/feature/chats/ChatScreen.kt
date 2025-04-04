package com.bed.chat.presentation.feature.chats

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.bed.chat.domain.models.output.ChatOutputModel
import com.bed.chat.domain.models.output.UserOutputModel

import com.bed.chat.presentation.feature.chats.component.ChatTopBar
import com.bed.chat.presentation.feature.chats.component.ChatLoading
import com.bed.chat.presentation.feature.chats.component.ChatFailure
import com.bed.chat.presentation.feature.chats.component.ChatSuccess

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatScaffold
import com.bed.chat.presentation.shared.preview.fake.userOneFake
import com.bed.chat.presentation.shared.preview.provider.ChatsPreviewParameterProvider

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigateToChat: (ChatOutputModel) -> Unit
) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        user = user,
        state = state,
        onClick = onNavigateToChat,
        onTryAgainClick = { viewModel.getChats(isRefresh = true) }
    )
}

@Composable
fun ChatScreen(
    user: UserOutputModel?,
    state: ChatViewModel.ChatState,
    onTryAgainClick: () -> Unit,
    onClick: (ChatOutputModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    ChatScaffold(
        modifier = modifier,
        topBar = { ChatTopBar(user = user?.lastName) },
        content = {
            when (state) {
                ChatViewModel.ChatState.Loading -> ChatLoading()
                ChatViewModel.ChatState.Failure -> ChatFailure(onTryAgainClick)
                is ChatViewModel.ChatState.Success -> ChatSuccess(
                    data = state.data,
                    onClick = { onClick(it) }
                )
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
            user = userOneFake,
            state = ChatViewModel.ChatState.Loading,
            onClick = {},
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
            user = userOneFake,
            state = ChatViewModel.ChatState.Failure,
            onClick = {},
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
            user = userOneFake,
            state = ChatViewModel.ChatState.Success(chats),
            onClick = {},
            onTryAgainClick = {}
        )
    }
}
