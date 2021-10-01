package ru.shafran.cards.ui.component.cardsdetails.activation

import ru.shafran.cards.data.card.ActivationDataModel
import ru.shafran.cards.data.card.CardModel

interface CardActivation {

    val card: CardModel
    fun onActivate(data: ActivationDataModel)
    fun onCancel()

}