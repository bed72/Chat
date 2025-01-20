package com.bed.chat.presentation.shared.routes

import kotlin.reflect.KClass

import androidx.annotation.StringRes
import androidx.annotation.DrawableRes

import kotlinx.serialization.Serializable

import com.bed.chat.R

sealed interface Routes {
    @Serializable
    object Splash

    @Serializable
    object SignIn

    @Serializable
    object SignUp

    @Serializable
    object Chat

    @Serializable
    object User

    @Serializable
    object Profile
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
