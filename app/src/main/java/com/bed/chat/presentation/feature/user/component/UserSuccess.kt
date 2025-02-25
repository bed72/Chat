package com.bed.chat.presentation.feature.user.component

import kotlinx.coroutines.flow.flowOf

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize

import com.bed.chat.domain.models.output.user.UserOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.preview.fake.userOneFake

import com.bed.chat.presentation.shared.components.UserItem
import com.bed.chat.presentation.shared.modifiers.noRippleClickable
import com.bed.chat.presentation.shared.components.loader.LoadMore
import com.bed.chat.presentation.shared.components.empty.EmptyDefault
import com.bed.chat.presentation.shared.components.loader.LoadFailure

@Composable
fun UserSuccess(
    onClicked: (userId: Int) -> Unit,
    users: LazyPagingItems<UserOutputModel>,
    modifier: Modifier = Modifier,
) {
    if (users.itemCount == 0) EmptyDefault(modifier = modifier)
    else
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(users.itemCount) { index ->
                users[index]?.let {
                    UserItem(
                        it,
                        modifier = Modifier.then(noRippleClickable { onClicked(it.id) })
                    )

                    if (index > users.itemCount) return@items

                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                }
            }

            if (users.loadState.append is LoadState.Loading) item { LoadMore() }

            if (users.loadState.append is LoadState.Error) item { LoadFailure { users.retry() } }
        }
}


@Preview
@Composable
private fun UserSuccessPreview() {
    ChatTheme {
        UserSuccess(
            onClicked = {},
            users = flowOf(PagingData.from(listOf(userOneFake))).collectAsLazyPagingItems()
        )
    }
}
