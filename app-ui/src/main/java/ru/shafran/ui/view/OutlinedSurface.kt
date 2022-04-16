package ru.shafran.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun OutlinedSurface(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .then(modifier),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, borderColor),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OutlinedSurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    content: @Composable () -> Unit,
) {
    OutlinedSurface(
        modifier = modifier.clickable(onClick = onClick),
        borderColor = borderColor,
        content = content
    )
}
