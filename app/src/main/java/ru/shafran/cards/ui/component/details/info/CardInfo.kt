package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard

interface CardInfo {


    val routerState: Value<RouterState<CardInfoConfig, Child>>
    fun onShowCard(card: DetectedCard)

    fun onShowCard(card: Card)

    sealed class Child {
        class Loading(val component: ru.shafran.cards.ui.component.details.info.Loading) : Child()
        class Success(val component: CardInfoLoaded) : Child()
    }
}