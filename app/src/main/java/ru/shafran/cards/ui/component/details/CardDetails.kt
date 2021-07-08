package ru.shafran.cards.ui.component.details

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.info.CardInfo
import ru.shafran.cards.utils.Task

interface CardDetails {
    val isShown: Value<Boolean>
    fun onShow(card: DetectedCard)
    fun onHide()

    val info: CardInfo
}