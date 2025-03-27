package com.bed.chat.presentation.feature.users

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

import com.bed.chat.presentation.feature.users.component.UsersTopBar
import com.bed.chat.presentation.feature.users.component.UsersLoading
import com.bed.chat.presentation.feature.users.component.UsersFailure
import com.bed.chat.presentation.feature.users.component.UsersSuccess

@Composable
fun UsersRoute(
    navigateToChat: (userId: Int) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    UsersScreen(
        users = viewModel.users.collectAsLazyPagingItems()
    ) { navigateToChat(it) }
}

@Composable
fun UsersScreen(
    users: LazyPagingItems<UserOutputModel>,
    onClicked: (userId: Int) -> Unit,
) {
    ChatScaffold(
        topBar = { UsersTopBar() },
        content = {
            when (users.loadState.refresh) {
                LoadState.Loading -> UsersLoading()
                is LoadState.Error -> UsersFailure(onTryAgainClick = { users.refresh() })
                is LoadState.NotLoading -> UsersSuccess(users = users, onClicked = { onClicked(it) })
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
           UsersScreen(
               onClicked = {},
               users = flowOf(PagingData.from(listOf(userOneFake))).collectAsLazyPagingItems()
           )
    }
}
