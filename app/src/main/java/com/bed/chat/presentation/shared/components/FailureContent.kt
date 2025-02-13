package com.bed.chat.presentation.shared.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.font.FontWeight

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun FailureContent(
    @StringRes title: Int,
    @StringRes message: Int,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
    resource: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        resource?.let { component ->
            Box(
                modifier = Modifier
                    .sizeIn(
                        maxWidth = 272.dp,
                        maxHeight = 272.dp,
                    ),
            ) {
                component()

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        Text(
            text = stringResource(id = title),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W500
            ),

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = message),
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )

        action?.let { component ->
            Spacer(modifier = Modifier.height(32.dp))

            component()
        }
    }
}

@Preview
@Composable
private fun FailurePreview() {
    ChatTheme {
        FailureContent(
            title = R.string.common_generic_error_title,
            message = R.string.common_generic_error_message,
            action = {
                PrimaryButton(
                    text = stringResource(id = R.string.common_generic_error_button_retry),
                    onClick = {}
                )
            },
            resource = {
                AnimatedContent(
                    modifier = Modifier.size(272.dp),
                )
            }
        )
    }
}
