package ru.shafran.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MaterialDivider(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.20f)
                .height(2.dp)
                .align(Alignment.Center)
                .background(
                    MaterialTheme.colorScheme.outline,
                )
        )
    }
}