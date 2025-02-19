package com.bed.chat.presentation.shared.components


import androidx.annotation.RawRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun AnimatedContent(
    modifier: Modifier = Modifier,
    @RawRes resId: Int = R.raw.animation_generic_error
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        modifier = modifier,
        progress = { progress },
        composition = composition
    )
}

@Preview
@Composable
private fun AnimatedContentPreview() {
    ChatTheme {
        AnimatedContent()
    }
}
