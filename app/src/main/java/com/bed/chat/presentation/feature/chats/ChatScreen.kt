package com.bed.chat.presentation.feature.chats

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.domain.models.output.ChatOutputModel
import com.bed.chat.presentation.shared.components.chat.ChatItem

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(state = state)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatViewModel.CheatUiState
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AnnotatedString.fromHtml(stringResource(R.string.chat_greeting, "Gabriel")),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                expandedHeight = 100.dp,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        content = { padding ->
            Box(
                modifier
                    .padding(padding)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp),
                        )
                    )
                    .clip(
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp),
                        )
                    )
                    .fillMaxSize(),
            ) {
                when (state) {
                    ChatViewModel.CheatUiState.Loading -> Loading()
                    ChatViewModel.CheatUiState.Failure -> Failure()
                    is ChatViewModel.CheatUiState.Success -> Success(data = state.data)
                }
            }
        }
    )
}

@Composable
private fun Loading() {
    Text(text = "Loading")
}

@Composable
private fun Failure() {
    Text(text = "Failure")
}

@Composable
private fun Success(data: List<ChatOutputModel>) {
    LazyColumn(
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

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
)
private fun ChatsScreenPreview() {
    ChatTheme {
        ChatScreen(
            state = ChatViewModel.CheatUiState.Loading
        )
    }
}
