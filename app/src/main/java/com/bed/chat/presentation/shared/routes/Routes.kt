package com.bed.chat.presentation.shared.routes

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    object Splash

    @Serializable
    object SignIn

    @Serializable
    object SignUp

    @Serializable
    object Chats
}
