package com.bed.chat.presentation.feature.user.component

import kotlinx.coroutines.flow.flowOf

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

import androidx.compose.runtime.Composable

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
import androidx.compose.material3.CircularProgressIndicator

import com.bed.chat.R

import com.bed.chat.domain.models.output.user.UserOutputModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.preview.fake.userOneFake

import com.bed.chat.presentation.shared.components.UserItem
import com.bed.chat.presentation.shared.components.EmptyContent
import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.AnimatedContent

import com.bed.chat.presentation.shared.modifiers.noRippleClickable

@Composable
fun UserSuccess(
    onClicked: (userId: Int) -> Unit,
    users: LazyPagingItems<UserOutputModel>,
    modifier: Modifier = Modifier,
) {
    if (users.itemCount == 0)
        EmptyContent(
            modifier = modifier,
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

@Composable
private fun LoadMore() {
    Box(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadFailure(onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
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
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp).height(46.dp)
        )
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
