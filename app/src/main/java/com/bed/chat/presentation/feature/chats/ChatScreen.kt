package com.bed.chat.presentation.feature.chats

import android.net.Uri
import android.content.Intent
import android.content.Context
import android.provider.Settings

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.bed.chat.presentation.shared.permissions.NotificationPermission
import com.bed.chat.presentation.shared.preview.provider.ChatsPreviewParameterProvider


private fun openSettings(context: Context) {
    context.startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .apply { data = Uri.fromParts("package", context.packageName, null) }
    )
}

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    onNavigateToChat: (ChatOutputModel) -> Unit
) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val (shouldNotificationPermission, setShouldNotificationPermission) = remember { mutableStateOf(false) }

    ChatScreen(
        user = user,
        state = state,
        onClick = onNavigateToChat,
        shouldPermissionBanner = shouldNotificationPermission,
        onDismiss = { setShouldNotificationPermission(false) },
        onGoToSettings = {
            openSettings(context)
            setShouldNotificationPermission(false)
        },
        onTryAgainClick = { viewModel.getChats(isRefresh = true) }
    )

    NotificationPermission(
        onDenied = {
            setShouldNotificationPermission(true)
        },
    )

}

@Composable
fun ChatScreen(
    user: UserOutputModel?,
    state: ChatViewModel.ChatState,
    shouldPermissionBanner: Boolean,
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit,
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
                    onDismiss = onDismiss,
                    onClick = { onClick(it) },
                    onGoToSettings = onGoToSettings,
                    shouldPermissionBanner = shouldPermissionBanner
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
            shouldPermissionBanner = false,
            onClick = {},
            onDismiss = {},
            onGoToSettings = {},
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
            shouldPermissionBanner = false,
            onClick = {},
            onDismiss = {},
            onGoToSettings = {},
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
            shouldPermissionBanner = false,
            onClick = {},
            onDismiss = {},
            onGoToSettings = {},
            onTryAgainClick = {}
        )
    }
}
