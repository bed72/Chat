package com.bed.chat.presentation.feature.user.component

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.size

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.FailureContent
import com.bed.chat.presentation.shared.components.AnimatedContent

@Composable
fun UserFailure(
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FailureContent(
        modifier = modifier,
        title = R.string.common_generic_error_title,
        message = R.string.common_generic_error_message,
        resource = { AnimatedContent(modifier = Modifier.size(200.dp),) },
        action = {
            PrimaryButton(
                text = R.string.common_generic_error_button_retry,
                onClick = onTryAgainClick
            )
        }
    )
}

@Preview
@Composable
private fun UserFailurePreview() {
    ChatTheme {
        UserFailure(onTryAgainClick = {})
    }
}
