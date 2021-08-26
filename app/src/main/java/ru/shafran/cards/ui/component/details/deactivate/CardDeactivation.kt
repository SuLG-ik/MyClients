package ru.shafran.cards.ui.component.details.deactivate

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DeactivationData

interface CardDeactivation {

    val card: StateFlow<Card>

    fun onDeactivate(data: DeactivationData)

    fun onCancel()

}