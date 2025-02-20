package com.bed.chat.presentation.feature.user

import kotlinx.coroutines.flow.flowOf

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.Composable

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator

import com.bed.chat.R

import com.bed.chat.domain.models.output.user.UserOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.TopBar
import com.bed.chat.presentation.shared.components.UserItem
import com.bed.chat.presentation.shared.components.ChatScaffold
import com.bed.chat.presentation.shared.components.EmptyContent
import com.bed.chat.presentation.shared.preview.fake.userOneFake
import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.FailureContent
import com.bed.chat.presentation.shared.components.AnimatedContent
import com.bed.chat.presentation.shared.components.skeleton.UserItemSkeleton

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
) {
    ChatScaffold(
        topBar = {
            TopBar(
                title = {
                    Text(
                        text = stringResource(R.string.user_title),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )
        },
        content = {
            when (users.loadState.refresh) {
                LoadState.Loading -> Loading()
                is LoadState.Error -> Failure { users.refresh() }
                is LoadState.NotLoading -> Success(users)
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
            UserItemSkeleton()

            if (it < 6) HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Composable
private fun Failure(
    onTryAgainClick: () -> Unit,
) {
    FailureContent(
        title = R.string.common_generic_error_title,
        message = R.string.common_generic_error_message,
        resource = { AnimatedContent(modifier = Modifier.size(200.dp),) },
        action = {
            PrimaryButton(
                text = R.string.common_generic_error_button_retry,
                onClick = onTryAgainClick
            )
        }
    )
}

@Composable
private fun Success(
    users: LazyPagingItems<UserOutputModel>,
) {
    if (users.itemCount == 0)
        EmptyContent(
            message = R.string.common_generic_error_empty,
            resource = {
                AnimatedContent(
                    modifier = Modifier.size(200.dp),
                    resId = R.raw.animation_empty_list
                )
            }
        )
    else
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(users.itemCount) { index ->
                users[index]?.let {
                    UserItem(it)

                    if (index > users.itemCount) return@items

                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                }
            }

            if (users.loadState.append is LoadState.Loading) item { LoadMore() }

            if (users.loadState.append is LoadState.Error) item { LoadFailure { users.retry() } }
        }
}

@Composable
private fun LoadMore() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadFailure(onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.common_generic_error_load_more),
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onErrorContainer
        )

        PrimaryButton(
            text = R.string.common_generic_error_button_retry,
            onClick = onTryAgainClick,
            modifier = Modifier.padding(horizontal = 30.dp).height(46.dp)
        )
    }
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

        UserScreen(users = users)
    }
}
