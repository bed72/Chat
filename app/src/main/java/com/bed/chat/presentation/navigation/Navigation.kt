package com.bed.chat.presentation.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.bed.chat.presentation.feature.signin.SignInInitScreen
import com.bed.chat.presentation.feature.splash.SplashInitScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashDestination) {
        composable<SplashDestination> {
            SplashInitScreen {
                navController.navigate(SignInDestination)
            }
        }
        composable<SplashDestination> {
            SignInInitScreen()
        }
    }
}
