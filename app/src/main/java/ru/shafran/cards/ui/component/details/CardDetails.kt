package ru.shafran.cards.ui.component.details

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.info.CardInfo

interface CardDetails {

    val isShown: StateFlow<Boolean>
    fun onShow(card: DetectedCard)
    fun onHide()
    val info: CardInfo

}