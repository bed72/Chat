package com.bed.chat.presentation

import androidx.compose.ui.Modifier

import androidx.compose.runtime.Composable

import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.consumeWindowInsets

import com.bed.chat.presentation.shared.routes.Router
import com.bed.chat.presentation.shared.components.BottomBar
import com.bed.chat.presentation.shared.routes.rememberRoutesState

@Composable
fun App(modifier: Modifier = Modifier) {
    val state = rememberRoutesState()

    Scaffold(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .consumeWindowInsets(it)
                    .padding(it)
                    .imePadding()
                    .fillMaxSize()
            ) { Router(state = state) }
        },
        bottomBar = {
            if (!state.topLevelRoutes.contains(state.currentTopLevelRoute)) return@Scaffold

            BottomBar(state = state)
        },
    )
}
