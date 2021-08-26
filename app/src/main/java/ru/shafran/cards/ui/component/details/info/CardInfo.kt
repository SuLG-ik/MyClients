package ru.shafran.cards.ui.component.details.info

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.Card

interface CardInfo {

    val currentCard: StateFlow<Card>

    fun onUse()
    fun onActivate()
    fun onDeactivate()

}