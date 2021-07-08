package ru.shafran.cards.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ShafranCardsTheme(
    theme: Theme = if (isSystemInDarkTheme()) Theme.Dark else Theme.Light,
    content: @Composable() () -> Unit,
) {
    MaterialTheme(
        colors = theme.colors,
        shapes = shapes,
        content = content
    )
}

sealed class Theme(val colors: Colors) {
    object Light : Theme(Colors(
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
        isLight = true
    ))

    object Dark : Theme(Colors(
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
        isLight = true
    ))
}