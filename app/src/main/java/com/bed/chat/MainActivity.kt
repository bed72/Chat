package com.bed.chat

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.navigation.compose.rememberNavController

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.bed.chat.presentation.theme.ChatTheme
import com.bed.chat.presentation.theme.systemBarStyle

import com.bed.chat.presentation.components.NavigationComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val isDarkTheme = isSystemInDarkTheme()

            enableEdgeToEdge(
                statusBarStyle = systemBarStyle(isDarkTheme),
                navigationBarStyle = systemBarStyle(isDarkTheme),
            )

            ChatTheme {
                NavigationComponent(navController = rememberNavController())
            }
        }
    }
}
