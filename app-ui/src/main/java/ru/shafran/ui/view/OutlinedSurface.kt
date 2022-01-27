package ru.shafran.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun OutlinedSurface(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.onBackground.copy(0.14f),
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .then(modifier),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, borderColor),
        content = content
    )
}
