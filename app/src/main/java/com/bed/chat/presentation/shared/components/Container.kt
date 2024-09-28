package com.bed.chat.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier

import androidx.compose.runtime.Composable

import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme

@Composable
fun Container(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .imePadding()
                .fillMaxSize()
                .safeDrawingPadding()
                .consumeWindowInsets(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            content(paddingValues)
        }
    }
}
