package com.bed.chat.presentation

import androidx.compose.ui.Modifier

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable

import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets

import com.bed.chat.presentation.shared.routes.Router
import com.bed.chat.presentation.shared.routes.RoutesState
import com.bed.chat.presentation.shared.components.BottomBar

@Composable
fun App(
    modifier: Modifier = Modifier,
    state: RoutesState
) {
    val topLevel = remember(state.topLevelRoutes) {
        state.topLevelRoutes.toSet()
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .consumeWindowInsets(it)
            ) { Router(state = state) }
        },
        bottomBar = {
            if (state.currentTopLevelRoute in topLevel) BottomBar(state = state)
        },
    )
}
