package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.session.data.SessionUsage

interface SessionUsageHistoryStore :
    Store<SessionUsageHistoryStore.Intent, SessionUsageHistoryStore.State, SessionUsageHistoryStore.Label> {

    sealed class Intent {
        data class LoadHistory(
            val page: Int = 0,
        ): Intent()
    }

    sealed class State {
        data class HistoryLoaded(
            val page: Int,
            val history: List<SessionUsage>,
        ) : State()

        data class Loading(
            val loadedHistory: List<SessionUsage>,
        ): State()

        sealed class Error : State() {
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }
    }

    sealed class Label {}

}