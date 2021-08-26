package ru.shafran.cards.ui.component.cards

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.CardDetails

interface Cards {
    fun onTakeCard()

    fun onShowCard(card: DetectedCard)
    fun onShowCard(card: Card)

    val details: CardDetails

    val routerState: Value<RouterState<CardsConfiguration, Child>>

    sealed class Child {

        class Camera(val camera: ru.shafran.cards.ui.component.camera.Camera) : Child()
        class UsagesList(val usagesList: ru.shafran.cards.ui.component.usages.UsagesList) : Child()

    }

}