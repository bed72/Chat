package com.bed.chat.presentation.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect

import com.bed.chat.R

@Composable
fun SplashRoute(
    onNavigateToChats: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LifecycleStartEffect(Unit) {
        viewModel.hasSession()

        onStopOrDispose {  }
    }

    LaunchedEffect(Unit) {
        viewModel.state.collect { state ->
            when (state) {
                SplashViewModel.AuthenticationState.UserAuthenticated -> onNavigateToChats()
                SplashViewModel.AuthenticationState.UserNotAuthenticated -> onNavigateToSignIn()
            }
        }
    }

    SplashScreen()
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            modifier = modifier
                .size(260.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_icon_description)
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun SplashScreenPreview() {
    SplashScreen()
}
