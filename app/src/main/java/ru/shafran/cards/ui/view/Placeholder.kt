package ru.shafran.cards.ui.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.widget.Placeholder
import ru.shafran.cards.R

@Composable
fun Placeholder(modifier: Modifier = Modifier) {
    Image(
        painterResource(id = R.drawable.placeholder),
        null,
        modifier = modifier,
    )
}