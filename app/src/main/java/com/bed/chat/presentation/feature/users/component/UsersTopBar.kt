package com.bed.chat.presentation.feature.users.component

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.TopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UsersTopBar(modifier: Modifier = Modifier) {
    TopBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.user_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

@Preview
@Composable
private fun UserTopBarPreview() {
    ChatTheme {
        UsersTopBar()
    }
}
