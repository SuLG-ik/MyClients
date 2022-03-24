package ru.shafran.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shafran.ui.theme.shapes

@Composable
internal fun FloatingBottomNavigation(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp,
        modifier = modifier,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline,
        ),
        shape = shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}