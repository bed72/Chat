package com.bed.chat.presentation.theme

import androidx.compose.material3.Typography

import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import com.bed.chat.R

private val interFamily = FontFamily(
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
)

val Typography = Typography(
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    headlineLarge = TextStyle(
        fontSize = 28.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    titleSmall = TextStyle(
        fontSize = 28.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    titleMedium = TextStyle(
        fontSize = 32.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    ),
    titleLarge = TextStyle(
        fontSize = 36.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Companion.W400
    )
)
