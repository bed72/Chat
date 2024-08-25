package com.bed.chat.presentation.shared.extensions

import androidx.navigation.NavBackStackEntry

import androidx.compose.animation.core.tween
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.AnimatedContentTransitionScope

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInTo(
    direction: AnimatedContentTransitionScope.SlideDirection
) : EnterTransition = slideIntoContainer(
    towards = direction,
    animationSpec = tween(durationMillis = 400)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutTo(
    direction: AnimatedContentTransitionScope.SlideDirection
) : ExitTransition = slideOutOfContainer(
    towards = direction,
    animationSpec = tween(durationMillis = 400)
)
