package com.bed.chat.presentation.shared.components

import coil.compose.AsyncImage

import androidx.annotation.StringRes
import androidx.annotation.DrawableRes

import androidx.compose.runtime.Composable

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import com.bed.chat.R
import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun RoundedAvatar(
    model: Any?,
    size: Dp = 60.dp,
    modifier: Modifier = Modifier,
    @DrawableRes defaultAvatar: Int = R.drawable.ic_profile,
    @StringRes description: Int = R.string.default_redound_avatar_description
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(size)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = model,
            contentDescription = stringResource(description),
            error = painterResource(defaultAvatar),
            fallback = painterResource(defaultAvatar),
            placeholder = painterResource(defaultAvatar),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        )
    }

}

@Preview
@Composable
private fun RedoundedAvatar() {
    ChatTheme {
        RoundedAvatar(
            model = null,
        )
    }
}
