package ru.shafran.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.shafran.ui.theme.shapes

@Composable
internal fun OutlinedSurface(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(shapes.medium)
            .then(modifier),
        shape = shapes.medium,
        border = BorderStroke(1.dp, borderColor),
        content = content
    )
}
