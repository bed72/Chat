package com.bed.chat.presentation.components

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .height(54.dp)
            .fillMaxWidth(),
        enabled = !isLoading,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        if (isLoading) CircularProgressIndicator(
            modifier = Modifier.size(24.dp).aspectRatio(1f),
            strokeCap = StrokeCap.Round
        )
        else Text(text = text)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(
        text = "Entrar",
        onClick = {}
    )
}
