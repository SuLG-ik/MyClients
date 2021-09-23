package ru.shafran.cards.ui.component.details.use

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.UsageDataModel

interface CardUsage {

    val card: StateFlow<CardModel>

    fun onUse(data: UsageDataModel)

    fun onCancel()

}