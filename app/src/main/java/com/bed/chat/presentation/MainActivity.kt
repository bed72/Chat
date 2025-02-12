package com.bed.chat.presentation

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.ui.Modifier

import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.isSystemInDarkTheme


import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.theme.systemBarStyle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            enableEdgeToEdge(navigationBarStyle = systemBarStyle(isSystemInDarkTheme()))

            ChatTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) { App() }
            }
        }
    }
}
