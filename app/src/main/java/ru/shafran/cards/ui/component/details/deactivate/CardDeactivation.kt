package ru.shafran.cards.ui.component.details.deactivate

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.DeactivationDataModel

interface CardDeactivation {

    val card: StateFlow<CardModel>

    fun onDeactivate(data: DeactivationDataModel)

    fun onCancel()

}