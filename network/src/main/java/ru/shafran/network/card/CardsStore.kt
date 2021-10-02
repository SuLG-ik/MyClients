package ru.shafran.network.card

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.card.ActivationData
import ru.shafran.network.data.card.Card
import ru.shafran.network.data.card.DeactivationData
import ru.shafran.network.data.card.UsageData


interface CardsStore: Store<CardsStore.Intent, CardsStore.State, Nothing> {

    sealed class Intent {
        object Hide : Intent()
        class LoadCardByToken(val cardToken: String) : Intent()
        class LoadCardByActivationId(val activationId: Long) : Intent()
        class ActivateCard(val cardId: Long, val data: ActivationData): Intent()
        class DeactivationCard(val cardId: Long, val data: DeactivationData): Intent()
        class UseCard(val cardId: Long, val data: UsageData): Intent()
    }

    sealed class State {
        object Hidden : State()
        object Loading : State()
        class CardLoaded(val card: Card) : State()
        sealed class Error : State(){
            object CardDoesNotExists : Error()
            object CardMustBeActivated : Error()
            object ConnectionLost: Error()
            object InternalServerError : Error()
            object UnknownError : Error()
        }
    }

}