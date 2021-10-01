package ru.shafran.network.history

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.card.CardAction


interface HistoryStore: Store<HistoryStore.Intent, HistoryStore.State, Nothing> {

    sealed class Intent {
        class LoadHistory(val page: Int = 0) : Intent()
    }

    sealed class State {
        object Loading : State()
        class HistoryLoaded(val page: Int, val history: List<CardAction>) : State()
        class Error(val exception: Exception) : State()
    }

}