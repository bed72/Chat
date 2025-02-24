package com.bed.chat.presentation.shared.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    modifier: Modifier = Modifier,
    expandedHeight: Dp = 72.dp,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary
    ),
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        colors = colors,
        actions = actions,
        windowInsets = windowInsets,
        scrollBehavior = scrollBehavior,
        expandedHeight = expandedHeight,
        navigationIcon = navigationIcon
    )
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBarPreview() {
    ChatTheme {
        TopBar(
            title = {
                Text(
                    text = AnnotatedString.fromHtml(stringResource(R.string.chat_title, "Gabriel")).toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.W400
                    )
                )
            }
        )
    }
}
