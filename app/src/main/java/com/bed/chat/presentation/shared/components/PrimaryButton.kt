package com.bed.chat.presentation.shared.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateContentSize

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Button(
        modifier = modifier
            .height(54.dp),
        onClick = onClick,
        enabled = !isLoading,
        shape = if (isLoading) MaterialTheme.shapes.extraLarge else MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
        )
    ) {
        Box(
            modifier = modifier.animateContentSize()
        ) {
            if (isLoading) CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f),
                strokeCap = StrokeCap.Round
            )
            else Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        }
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
