package com.bed.chat

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.ui.Modifier

import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize

import androidx.navigation.compose.rememberNavController

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.theme.systemBarStyle
import com.bed.chat.presentation.shared.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val isDarkTheme = isSystemInDarkTheme()

            enableEdgeToEdge(
                navigationBarStyle = systemBarStyle(isDarkTheme),
            )

            ChatTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    Navigation(navController = rememberNavController())
                }
            }
        }
    }
}
