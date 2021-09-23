package ru.shafran.cards.ui.component.details.activation

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.ActivationDataModel
import ru.shafran.cards.data.card.CardModel

interface CardActivation {

    val card: StateFlow<CardModel>
    fun onActivate(data: ActivationDataModel)
    fun onCancel()

}