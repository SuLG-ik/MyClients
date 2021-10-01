package ru.shafran.cards.ui.component.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.cards.ui.component.camera.CameraUI
import ru.shafran.cards.ui.component.cardsdetails.CardDetailsUI

@Composable
fun CardsUI(component: Cards, modifier: Modifier = Modifier) {
    CardDetailsUI(component = component.details, modifier) {
        CameraUI(component = component.camera, modifier = Modifier.fillMaxSize())
    }
}