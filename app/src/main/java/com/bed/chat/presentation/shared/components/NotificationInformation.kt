package com.bed.chat.presentation.shared.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth

import com.bed.chat.R
import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun NotificationInformation(
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.onSecondary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.banner_notification_title),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.banner_notification_description),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                NotificationButton(onClick = onDismiss, title = R.string.banner_notification_close)

                Spacer(modifier = Modifier.width(4.dp))

                NotificationButton(onClick = onGoToSettings, title = R.string.banner_notification_open_config)
            }
        }
    }
}

@Composable
private fun NotificationButton(
    @StringRes title: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview
@Composable
private fun NotificationInformationPreview() {
    ChatTheme {
        NotificationInformation(
            onDismiss = {},
            onGoToSettings = {}
        )
    }
}
