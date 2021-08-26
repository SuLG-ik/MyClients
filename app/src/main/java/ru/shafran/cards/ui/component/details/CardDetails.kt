package ru.shafran.cards.ui.component.details

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.activation.CardActivation
import ru.shafran.cards.ui.component.details.deactivate.CardDeactivation
import ru.shafran.cards.ui.component.details.error.CardError
import ru.shafran.cards.ui.component.details.info.CardInfo
import ru.shafran.cards.ui.component.details.use.CardUsage

interface CardDetails {

    val isShown: StateFlow<Boolean>

    fun onHide()

    fun onShow(card: DetectedCard)

    fun onUse(card: Card)

    fun onActivate(card: Card)

    fun onDeactivate(card: Card)

    val state: Value<RouterState<CardDetailsConfiguration, Child>>

    fun onShow(card: Card)

    sealed class Child {
        class Loading(val loading: ru.shafran.cards.ui.component.details.loading.Loading): Child()
        class Info(val info: CardInfo): Child()
        class Activation(val activation: CardActivation): Child()
        class Usage(val usage: CardUsage): Child()
        class Deactivation(val deactivation: CardDeactivation): Child()
        class Error(val error: CardError): Child()
    }
}