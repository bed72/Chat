package com.bed.chat.presentation

import android.os.Bundle
import android.content.Intent

import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.NavController

import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.bed.chat.presentation.shared.routes.Routes
import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.routes.rememberRoutesState


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            ChatTheme {
                val state = rememberRoutesState()
                navController = state.navController

                App(state = state)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW && intent.data != null && intent.data!!.scheme == "chat") {
            val data = intent.data!!

            when (data.host) {
                "chat_detail" -> {
                    val id = data.lastPathSegment?.toInt() ?: return
                    navController.navigate(Routes.Message(id))
                }
            }
        }
    }
}
