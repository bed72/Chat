package com.bed.chat.presentation.components

import androidx.compose.ui.Modifier

import androidx.compose.runtime.Composable

import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues

@Composable
fun Container(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = modifier.imePadding().fillMaxSize()
        ) {
            content(paddingValues)
        }
    }
}
