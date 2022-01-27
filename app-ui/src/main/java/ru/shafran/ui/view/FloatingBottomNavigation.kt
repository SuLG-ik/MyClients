package ru.shafran.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults.OutlinedBorderOpacity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
internal fun FloatingBottomNavigation(
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        modifier = modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colors.onBackground.copy(alpha = OutlinedBorderOpacity),
                ),
                shape = MaterialTheme.shapes.medium,
            ),
        content = content,
    )
}