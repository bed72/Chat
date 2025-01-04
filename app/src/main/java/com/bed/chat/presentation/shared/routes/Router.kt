package com.bed.chat.presentation.shared.routes

import androidx.compose.runtime.Composable

import androidx.navigation.navOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import androidx.compose.animation.AnimatedContentTransitionScope

import com.bed.chat.presentation.feature.chats.ChatsRoute
import com.bed.chat.presentation.feature.splash.SplashRoute
import com.bed.chat.presentation.feature.signin.SignInRoute
import com.bed.chat.presentation.feature.signup.SignUpRoute

import com.bed.chat.presentation.shared.extensions.slideInTo
import com.bed.chat.presentation.shared.extensions.slideOutTo

@Composable
fun Router(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Splash) {
        composable<Routes.Splash> {
            SplashRoute(
                onNavigateToChats = {
                    navController.navigate(
                        route = Routes.Chats,
                        navOptions = navOptions {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    )
                },
                onNavigateToSignIn = {
                    navController.navigate(
                        route = Routes.SignIn,
                        navOptions = navOptions {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    )
                }
            )
        }

        composable<Routes.SignIn>(
            enterTransition = { slideInTo(AnimatedContentTransitionScope.SlideDirection.Right) },
            exitTransition = { slideOutTo(AnimatedContentTransitionScope.SlideDirection.Left) }
        ) {
            SignInRoute(
                onNavigateToSignUp = { navController.navigate(Routes.SignUp) },
                onNavigateToChats = {
                    navController.navigate(
                        route = Routes.Chats,
                        navOptions = navOptions {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    )
                }
            )
        }

        composable<Routes.SignUp>(
            enterTransition = { slideInTo(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutTo(AnimatedContentTransitionScope.SlideDirection.Right) }
        ) {{}
            SignUpRoute(
                onNavigateToSignIn = { navController.navigate(Routes.SignIn) }
            )
        }

        composable<Routes.Chats>(
            enterTransition = { slideInTo(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutTo(AnimatedContentTransitionScope.SlideDirection.Right) }
        ) {
            ChatsRoute()
        }
    }
}
