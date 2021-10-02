package ru.shafran.cards.ui.component.cardsdetails

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.ui.component.cardsdetails.activation.CardActivation
import ru.shafran.cards.ui.component.cardsdetails.deactivate.CardDeactivation
import ru.shafran.cards.ui.component.cardsdetails.error.CardError
import ru.shafran.cards.ui.component.cardsdetails.info.CardInfo
import ru.shafran.cards.ui.component.cardsdetails.use.CardUsage

interface CardDetails {

    val isShown: StateFlow<Boolean>

    fun onHide()

    fun onShowByCardToken(cardToken: String)
    fun onShowByActivationId(activationId: Long)

    fun onUse(card: CardModel)

    fun onActivate(card: CardModel)

    fun onDeactivate(card: CardModel)

    val state: Value<RouterState<CardDetailsConfiguration, Child>>

    sealed class Child {
        object Hidden : Child()
        class Loading(val loading: ru.shafran.cards.ui.component.loading.Loading) : Child()
        class Info(val info: CardInfo) : Child()
        class Activation(val activation: CardActivation) : Child()
        class Usage(val usage: CardUsage) : Child()
        class Deactivation(val deactivation: CardDeactivation) : Child()
        class Error(val error: CardError) : Child()
    }
}