package ru.shafran.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun ShafranCardsTheme(
    theme: Theme = if (isSystemInDarkTheme()) Theme.Light else Theme.Light,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = theme.colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

internal sealed class Theme(val colors: Colors) {
    object Light : Theme(
        Colors(
            primary = lightPrimary,
            primaryVariant = lightPrimaryVariant,
            secondary = lightSecondary,
            secondaryVariant = lightSecondaryVariant,
            background = lightBackground,
            surface = lightSurface,
            error = lightError,
            onPrimary = lightOnPrimary,
            onSecondary = lightOnSecondary,
            onBackground = lightOnBackground,
            onSurface = lightOnSurface,
            onError = lightOnError,
            isLight = true,
        )
    )
}