package com.bed.chat.presentation.feature.message

import androidx.compose.foundation.layout.imePadding
import kotlinx.coroutines.flow.Flow

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.LifecycleResumeEffect

import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatScaffold

import com.bed.chat.presentation.feature.message.component.MessageTopBar
import com.bed.chat.presentation.feature.message.component.MessageContent
import com.bed.chat.presentation.shared.preview.provider.MessagePreviewParameterProvider

@Composable
fun MessageRoute(
    goBack: () -> Unit,
    viewModel: MessageViewModel = hiltViewModel()
) {
    val hostState = remember { SnackbarHostState() }
    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()
    val userState by viewModel.userState.collectAsStateWithLifecycle()
    val messages = viewModel.messages.collectAsLazyPagingItems()
    val (showFailure, setShowFailure) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.showFailureState.collect { setShowFailure(it) }
    }

    LaunchedEffect(messages.loadState.refresh) {
        viewModel.setMessagesState(messages.loadState.refresh)
    }

    LaunchedEffect(showFailure) {
        if (showFailure)
            hostState.showSnackbar(
                "Opps, uma falha aconteceu!!!",
                duration = SnackbarDuration.Short
            ).also {
                viewModel.dismissShowFailure()
                if (it == SnackbarResult.Dismissed) goBack()
            }
    }

    LifecycleResumeEffect(Unit) {
        viewModel.onResume()

        onPauseOrDispose {
            viewModel.onPause()
        }
    }

    MessageScreen(
        goBack = goBack,
        isOnline = isOnline,
        messages = messages,
        userState = userState,
        hostState = hostState,
        message = viewModel.message,
        onSendMessage = viewModel::onSendMessage,
        onMessageChange = viewModel::onMessageChange
    )
}

@Composable
fun MessageScreen(
    message: String,
    isOnline: Boolean,
    goBack: () -> Unit,
    onSendMessage: () -> Unit,
    hostState: SnackbarHostState,
    onMessageChange: (String) -> Unit,
    userState: MessageViewModel.UserState,
    messages: LazyPagingItems<MessageOutputModel>,
    modifier: Modifier = Modifier,
) {


    ChatScaffold(
        modifier = modifier.imePadding(),
        topBar = { MessageTopBar(state = userState, isOnline = isOnline, goBack = goBack) },
        content = {
            MessageContent(
                message = message,
                messages = messages,
                onSendMessage = onSendMessage,
                onMessageChange = onMessageChange,
            )
        },
        snackbarHost = { SnackbarHost(hostState = hostState) }
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
            isOnline = false,
            onSendMessage = {},
            onMessageChange = {},
            hostState = remember { SnackbarHostState() },
            messages = messages.collectAsLazyPagingItems(),
            userState = MessageViewModel.UserState.Loading,
        )
    }
}
