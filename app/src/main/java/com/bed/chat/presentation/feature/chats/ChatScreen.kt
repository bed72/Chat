package com.bed.chat.presentation.feature.chats

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.bed.chat.R

import com.bed.chat.domain.models.output.chat.ChatOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.presentation.shared.components.TopBar
import com.bed.chat.presentation.shared.components.ChatItem
import com.bed.chat.presentation.shared.components.EmptyContent
import com.bed.chat.presentation.shared.components.ChatScaffold
import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.FailureContent
import com.bed.chat.presentation.shared.components.AnimatedContent
import com.bed.chat.presentation.shared.components.skeleton.ChatItemSkeleton

import com.bed.chat.presentation.shared.preview.provider.ChatsPreviewParameterProvider

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onTryAgainClick = { viewModel.getChats() })
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatViewModel.CheatUiState,
    onTryAgainClick: () -> Unit,
) {
    ChatScaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = {
                    Text(
                        text = AnnotatedString.fromHtml(stringResource(R.string.chat_title, "Gabriel")),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )
        },
        content = {
            when (state) {
                ChatViewModel.CheatUiState.Loading -> Loading()
                ChatViewModel.CheatUiState.Failure -> Failure(onTryAgainClick)
                is ChatViewModel.CheatUiState.Success -> Success(state.data)
            }
        }
    )
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        repeat(6) {
            ChatItemSkeleton()

            if (it < 6) HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Composable
private fun Failure(onTryAgainClick: () -> Unit,) {
    FailureContent(
        title = R.string.common_generic_error_title,
        message = R.string.common_generic_error_message,
        resource = {
            AnimatedContent(modifier = Modifier.size(200.dp))
        },
        action = {
            PrimaryButton(
                text = R.string.common_generic_error_button_retry,
                onClick = onTryAgainClick
            )
        },
    )
}

@Composable
private fun Success(data: List<ChatOutputModel>) {
    when (data.isNotEmpty()) {
        false -> EmptyContent(
            message = R.string.common_generic_error_empty,
            resource = {
                AnimatedContent(
                    modifier = Modifier.size(200.dp),
                    resId = R.raw.animation_empty_list
                )
            }
        )
        true -> LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            @Suppress("MagicNumber")
            itemsIndexed(data) { index, model ->
                ChatItem(model)

                if (index > data.lastIndex) return@itemsIndexed

                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
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
