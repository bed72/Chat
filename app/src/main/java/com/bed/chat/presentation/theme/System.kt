package com.bed.chat.presentation.theme

import android.graphics.Color
import androidx.activity.SystemBarStyle

private val systemBarDark = Color.parseColor("#121318")
private val systemBarLight = Color.parseColor("#F8F6FD")

private val dark = SystemBarStyle.dark(systemBarDark)
private val light = SystemBarStyle.light(systemBarLight, systemBarDark)

fun systemBarStyle(isDarkTheme: Boolean) = if (isDarkTheme) dark else light
