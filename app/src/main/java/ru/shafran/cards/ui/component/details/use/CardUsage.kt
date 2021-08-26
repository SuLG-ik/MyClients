package ru.shafran.cards.ui.component.details.use

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.UsageData

interface CardUsage {

    val card: StateFlow<Card>

    fun onUse(data: UsageData)

    fun onCancel()

}