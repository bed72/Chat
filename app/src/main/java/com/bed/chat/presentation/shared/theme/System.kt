package com.bed.chat.presentation.shared.theme

import android.graphics.Color
import androidx.activity.SystemBarStyle

private val systemBarDark = Color.parseColor("#000D1412")
private val systemBarLight = Color.parseColor("#00F1F8F3")

private val dark = SystemBarStyle.dark(systemBarDark)
private val light = SystemBarStyle.light(systemBarLight, systemBarDark)

fun systemBarStyle(isDarkTheme: Boolean) = if (isDarkTheme) dark else light
