package com.bed.chat.presentation.feature.chats.component

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.TopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChatTopBar(
    user: String?,
    modifier: Modifier = Modifier
) {
    TopBar(
        modifier = modifier,
        title = {
            Text(
                text = AnnotatedString.fromHtml(stringResource(R.string.chat_title, user ?: R.string.messages_is_offline)),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

@Preview
@Composable
private fun ChatTopBarPreview() {
    ChatTheme {
        ChatTopBar(user = "Gabriel")
    }
}
