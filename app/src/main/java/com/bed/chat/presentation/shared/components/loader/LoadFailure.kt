package com.bed.chat.presentation.shared.components.loader

import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.PrimaryButton

@Composable
fun LoadFailure(
    modifier: Modifier = Modifier,
    onTryAgainClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.common_generic_error_load_more),
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onErrorContainer
        )

        PrimaryButton(
            text = R.string.common_generic_error_button_retry,
            onClick = onTryAgainClick,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp).height(46.dp)
        )
    }
}

@Preview
@Composable
private fun LoadFailurePreview() {
    ChatTheme {
        LoadFailure()
    }
}
