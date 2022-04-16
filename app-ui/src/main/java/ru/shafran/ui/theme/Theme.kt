package ru.shafran.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme as MaterialTheme3

@Composable
internal fun ShafranCardsTheme(
    theme3: Theme3 = if (isSystemInDarkTheme()) Theme3.Light else Theme3.Light,
    content: @Composable () -> Unit,
) {
    MaterialTheme3(
        colorScheme = theme3.colors,
        shapes = shapes3,
        content = content,
    )
}

//internal sealed class Theme(val colors: Colors) {
//    object Light : Theme(
//        Colors(
//            primary = lightPrimary,
//            primaryVariant = lightPrimaryVariant,
//            secondary = lightSecondary,
//            secondaryVariant = lightSecondaryVariant,
//            background = lightBackground,
//            surface = lightSurface,
//            error = lightError,
//            onPrimary = lightOnPrimary,
//            onSecondary = lightOnSecondary,
//            onBackground = lightOnBackground,
//            onSurface = lightOnSurface,
//            onError = lightOnError,
//            isLight = true,
//        )
//    )
//}

internal sealed class Theme3(val colors: ColorScheme) {
    object Light : Theme3(
        ColorScheme(
            primary = lightPrimary,
            onPrimary = lightOnPrimary,
            primaryContainer = lightPrimaryContainer,
            onPrimaryContainer = lightOnPrimaryContainer,
            inversePrimary = lightUnknown,
            secondary = lightSecondary,
            onSecondary = lightOnSecondary,
            secondaryContainer = lightSecondaryContainer,
            onSecondaryContainer = lightOnSecondaryContainer,
            tertiary = lightUnknown,
            onTertiary = lightOnUnknown,
            tertiaryContainer = lightUnknown,
            onTertiaryContainer = lightOnUnknown,
            background = lightBackground,
            onBackground = lightOnBackground,
            surface = lightSurface,
            onSurface = lightOnSurface,
            surfaceVariant = lightSurfaceVariant,
            onSurfaceVariant = lightOnSurfaceVariant,
            surfaceTint = lightUnknown,
            inverseSurface = lightUnknown,
            inverseOnSurface = lightOnUnknown,
            error = lightError,
            onError = lightOnError,
            errorContainer = lightUnknown,
            onErrorContainer = lightOnUnknown,
            outline = lightOutline,
        )
    )
}