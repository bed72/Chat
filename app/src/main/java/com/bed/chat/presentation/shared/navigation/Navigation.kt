package com.bed.chat.presentation.shared.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.navOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import androidx.compose.animation.AnimatedContentTransitionScope

import com.bed.chat.presentation.shared.extensions.slideInTo
import com.bed.chat.presentation.shared.extensions.slideOutTo

import com.bed.chat.presentation.feature.splash.SplashInitScreen
import com.bed.chat.presentation.feature.signin.SignInInitScreen
import com.bed.chat.presentation.feature.signup.SignUpInitScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashDestination) {
        composable<SplashDestination> {
            SplashInitScreen {
                navController.navigate(
                    route = SignInDestination,
                    navOptions = navOptions {
                        popUpTo(SplashDestination) { inclusive = true }
                    }
                )
            }
        }
        composable<SignInDestination>(
            enterTransition = { slideInTo(AnimatedContentTransitionScope.SlideDirection.Right) },
            exitTransition = { slideOutTo(AnimatedContentTransitionScope.SlideDirection.Left) }
        ) {
            SignInInitScreen(
                onNavigateToSignUp = { navController.navigate(SignUpDestination) }
            )
        }
        composable<SignUpDestination>(
            enterTransition = { slideInTo(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutTo(AnimatedContentTransitionScope.SlideDirection.Right) }
        ) {{}
            SignUpInitScreen()
        }
    }
}
