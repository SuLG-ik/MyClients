package ru.shafran.cards.ui.component.cardsdetails.info

import ru.shafran.cards.data.card.CardModel

interface CardInfo {

    val currentCard: CardModel

    fun onUse()
    fun onActivate()
    fun onDeactivate()

}