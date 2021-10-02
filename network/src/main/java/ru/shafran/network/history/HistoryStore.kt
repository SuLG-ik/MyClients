package ru.shafran.network.history

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.card.CardAction


interface HistoryStore : Store<HistoryStore.Intent, HistoryStore.State, Nothing> {

    sealed class Intent {
        class LoadHistory(val page: Int = 0) : Intent()
    }

    sealed class State {
        object Loading : State()
        class HistoryLoaded(val history: List<CardAction>) : State()
        sealed class Error : State() {
            object InternalServerError : Error()
            object ConnectionLost: Error()
            object UnknownError : Error()
        }
    }
}
