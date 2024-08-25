package com.bed.chat.presentation.shared.components

import androidx.annotation.StringRes
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Arrangement

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun Header(
    @DrawableRes image: Int,
    @StringRes imageDescription: Int,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.2f))
                    .size(48.dp),
                painter = painterResource(id = image),
                contentDescription = stringResource(id = imageDescription)
            )

            Spacer(modifier = modifier.width(16.dp))

            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
        }

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = stringResource(id = subtitle),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    ChatTheme {
        Header(
            image = R.drawable.ic_launcher_foreground,
            imageDescription = R.string.app_icon_description,
            title = R.string.sign_in_title,
            subtitle = R.string.sign_in_sub_title
        )
    }
}
