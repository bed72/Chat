package com.bed.chat.presentation.components

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.bed.chat.presentation.feature.splash.SplashScreen
import com.bed.chat.presentation.feature.splash.SplashDestination

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashDestination) {
        composable<SplashDestination> {
            SplashScreen()
        }
    }
}
