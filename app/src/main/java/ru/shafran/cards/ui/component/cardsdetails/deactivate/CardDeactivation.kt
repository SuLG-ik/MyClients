package ru.shafran.cards.ui.component.cardsdetails.deactivate

import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.DeactivationDataModel

interface CardDeactivation {

    val card: CardModel

    fun onDeactivate(data: DeactivationDataModel)

    fun onCancel()

}