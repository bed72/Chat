package com.bed.chat.presentation.shared.theme

import android.os.Build

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

private val lightScheme = lightColorScheme(
    primary = lightPrimary,
    onPrimary = lightOnPrimary,
    primaryContainer = lightPrimaryContainer,
    onPrimaryContainer = lightOnPrimaryContainer,
    secondary = lightSecondary,
    onSecondary = lightOnSecondary,
    secondaryContainer = lightSecondaryContainer,
    onSecondaryContainer = lightOnSecondaryContainer,
    tertiary = lightTertiary,
    onTertiary = lightOnTertiary,
    tertiaryContainer = lightTertiaryContainer,
    onTertiaryContainer = lightOnTertiaryContainer,
    error = lightError,
    errorContainer = lightErrorContainer,
    onError = lightOnError,
    onErrorContainer = lightOnErrorContainer,
    background = lightBackground,
    onBackground = lightOnBackground,
    surface = lightSurface,
    onSurface = lightOnSurface,
    surfaceVariant = lightSurfaceVariant,
    onSurfaceVariant = lightOnSurfaceVariant,
    outline = lightOutline,
    inverseOnSurface = lightInverseOnSurface,
    inverseSurface = lightInverseSurface,
    inversePrimary = lightInversePrimary,
    surfaceTint = lightSurfaceTint,
    outlineVariant = lightOutlineVariant,
    scrim = lightScrim,
)

private val darkScheme = darkColorScheme(
    primary = darkPrimary,
    onPrimary = darkOnPrimary,
    primaryContainer = darkPrimaryContainer,
    onPrimaryContainer = darkOnPrimaryContainer,
    secondary = darkSecondary,
    onSecondary = darkOnSecondary,
    secondaryContainer = darkSecondaryContainer,
    onSecondaryContainer = darkOnSecondaryContainer,
    tertiary = darkTertiary,
    onTertiary = darkOnTertiary,
    tertiaryContainer = darkTertiaryContainer,
    onTertiaryContainer = darkOnTertiaryContainer,
    error = darkError,
    errorContainer = darkErrorContainer,
    onError = darkOnError,
    onErrorContainer = darkOnErrorContainer,
    background = darkBackground,
    onBackground = darkOnBackground,
    surface = darkSurface,
    onSurface = darkOnSurface,
    surfaceVariant = darkSurfaceVariant,
    onSurfaceVariant = darkOnSurfaceVariant,
    outline = darkOutline,
    inverseOnSurface = darkInverseOnSurface,
    inverseSurface = darkInverseSurface,
    inversePrimary = darkInversePrimary,
    surfaceTint = darkSurfaceTint,
    outlineVariant = darkOutlineVariant,
    scrim = darkScrim,
)

@Composable
fun ChatTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val scheme = schemeColor(isDarkTheme, isDynamicColor)

    MaterialTheme(
        content = content,
        colorScheme = scheme,
        typography = Typography
    )
}

@Composable
private fun schemeColor(isDarkTheme: Boolean, isDynamicColor: Boolean) =
    when {
        isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            if (isDarkTheme) dynamicDarkColorScheme(LocalContext.current)
            else dynamicLightColorScheme(LocalContext.current)
        isDarkTheme -> darkScheme
        else -> lightScheme
    }
