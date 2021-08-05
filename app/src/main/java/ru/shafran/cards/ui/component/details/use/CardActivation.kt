package ru.shafran.cards.ui.component.details.use

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.ActivationData
import ru.shafran.cards.data.card.Card

interface CardActivation {

    val card: StateFlow<Card>
    fun onActivate(data: ActivationData)

}