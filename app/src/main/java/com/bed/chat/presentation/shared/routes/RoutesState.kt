package com.bed.chat.presentation.shared.routes

import android.annotation.SuppressLint

import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf

import androidx.navigation.navOptions
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute

@Stable
class RoutesState(val navController: NavHostController) {
    private val previousRoute = mutableStateOf<NavDestination?>(null)

    val topLevelRoutes: List<TopLevelRoutes> = TopLevelRoutes.entries

    val currentRoute: NavDestination?
        @Composable
        get() {
            val current = navController.currentBackStackEntryFlow.collectAsState(initial = null)

            return current.value?.destination.also {
                if (it != null) previousRoute.value = it
            } ?: previousRoute.value
        }

    val currentTopLevelRoute: TopLevelRoutes?
        @Composable
        get() = topLevelRoutes.firstOrNull { currentRoute?.hasRoute(it.route) == true }

    fun navigateToTopLevel(route: TopLevelRoutes) {
        val navOptions = navOptions {
            restoreState = true
            launchSingleTop = true
            popUpTo(Routes.Chat) { saveState = true }
        }

        when (route) {
            TopLevelRoutes.CHAT -> navController.navigate(Routes.Chat, navOptions)
            TopLevelRoutes.USERS -> navController.navigate(Routes.User, navOptions)
            TopLevelRoutes.PROFILE -> navController.navigate(Routes.Profile, navOptions)
        }
    }
}

@Composable
@SuppressLint("ComposableNaming")
fun rememberRoutesState(navController: NavHostController = rememberNavController()): RoutesState =
    remember(navController) { RoutesState(navController) }
