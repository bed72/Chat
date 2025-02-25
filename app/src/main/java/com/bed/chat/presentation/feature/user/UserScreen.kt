package com.bed.chat.presentation.feature.user

import kotlinx.coroutines.flow.flowOf

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview

import com.bed.chat.domain.models.output.UserOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.ChatScaffold
import com.bed.chat.presentation.shared.preview.fake.userOneFake

import com.bed.chat.presentation.feature.user.component.UserTopBar
import com.bed.chat.presentation.feature.user.component.UserLoading
import com.bed.chat.presentation.feature.user.component.UserFailure
import com.bed.chat.presentation.feature.user.component.UserSuccess

@Composable
fun UserRoute(
    navigateToChat: (userId: Int) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    UserScreen(
        users = viewModel.users.collectAsLazyPagingItems()
    ) { navigateToChat(it) }
}

@Composable
fun UserScreen(
    users: LazyPagingItems<UserOutputModel>,
    onClicked: (userId: Int) -> Unit,
) {
    ChatScaffold(
        topBar = { UserTopBar() },
        content = {
            when (users.loadState.refresh) {
                LoadState.Loading -> UserLoading()
                is LoadState.Error -> UserFailure(onTryAgainClick = { users.refresh() })
                is LoadState.NotLoading -> UserSuccess(users = users, onClicked = { onClicked(it) })
            }
        }
    )
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
)
private fun ChatsScreenPreview() {
    ChatTheme {
           UserScreen(
               onClicked = {},
               users = flowOf(PagingData.from(listOf(userOneFake))).collectAsLazyPagingItems()
           )
    }
}
