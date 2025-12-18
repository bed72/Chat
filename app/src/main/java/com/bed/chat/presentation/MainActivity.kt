package com.bed.chat.presentation

import android.os.Bundle
import android.content.Intent

import kotlinx.coroutines.launch

import dagger.hilt.android.AndroidEntryPoint

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController

import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.bed.chat.presentation.shared.routes.Routes
import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.routes.rememberRoutesState
import com.bed.chat.presentation.shared.viewmodel.SessionViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    private val viewModel: SessionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        observeSessionExpired()

        setContent {
            ChatTheme {
                val state = rememberRoutesState()
                state.startRoute = if (intent.data == null) Routes.Splash else Routes.Chat

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

    private fun observeSessionExpired() {
        lifecycleScope.launch {
            viewModel.state.collect {
                viewModel.logout()

                navController.navigate(Routes.SignIn) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}
