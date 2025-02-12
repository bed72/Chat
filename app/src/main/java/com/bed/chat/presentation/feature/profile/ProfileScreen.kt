package com.bed.chat.presentation.feature.profile

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun ProfileRoute() {
    ProfileScreen()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile")
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = padding
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
