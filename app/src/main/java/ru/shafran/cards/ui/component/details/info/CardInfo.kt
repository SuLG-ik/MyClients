package ru.shafran.cards.ui.component.details.info

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.CardModel

interface CardInfo {

    val currentCard: StateFlow<CardModel>

    fun onUse()
    fun onActivate()
    fun onDeactivate()

}