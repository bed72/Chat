package com.bed.chat.presentation.shared.routes

import kotlin.reflect.KClass

import androidx.annotation.StringRes
import androidx.annotation.DrawableRes

import kotlinx.serialization.Serializable

import com.bed.chat.R

sealed interface Routes {
    @Serializable
    data object Splash : Routes

    @Serializable
    data object SignIn : Routes

    @Serializable
    data object SignUp : Routes

    @Serializable
    data object Chat : Routes

    @Serializable
    data object User : Routes

    @Serializable
    data object Profile : Routes

    @Serializable
    data class Message(val userId: Int) : Routes
}

enum class TopLevelRoutes(
    val route: KClass<*>,
    @StringRes val title: Int?,
    @DrawableRes val icon: Int?,
) {
    CHAT(
        icon = R.drawable.ic_chat,
        route = Routes.Chat::class,
        title = R.string.bottom_navigation_item_chat,
    ),
    USERS(
        icon = null,
        title = null,
        route = Routes.User::class,
    ),
    PROFILE(
        icon = R.drawable.ic_profile,
        route = Routes.Profile::class,
        title = R.string.bottom_navigation_item_profile,
    ),
}
