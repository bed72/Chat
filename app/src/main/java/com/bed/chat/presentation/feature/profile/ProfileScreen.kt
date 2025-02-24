package com.bed.chat.presentation.feature.profile

import androidx.compose.runtime.Composable

import androidx.compose.material3.Text

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.TopBar
import com.bed.chat.presentation.shared.components.ChatScaffold

@Composable
fun ProfileRoute() {
    ProfileScreen()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    ChatScaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                @Suppress("MagicNumber")
                items(100) {
                    Text(text = "Item $it")
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
        ProfileScreen()
    }
}
