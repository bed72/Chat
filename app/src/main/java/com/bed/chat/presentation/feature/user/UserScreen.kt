package com.bed.chat.presentation.feature.user

import kotlinx.coroutines.flow.flowOf

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.domain.models.output.user.UserOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.preview.fake.userOneFake

@Composable
fun UserRoute(
    viewModel: UserViewModel = hiltViewModel()
) {
    val users = viewModel.users.collectAsLazyPagingItems()

    UserScreen(users)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserScreen(
    users: LazyPagingItems<UserOutputModel>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "User")
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(users.itemCount) { index ->
                    val user = users[index]


                }
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
        val users =
            flowOf(PagingData.from(listOf(userOneFake))).collectAsLazyPagingItems()

        UserScreen(
            users = users
        )
    }
}
