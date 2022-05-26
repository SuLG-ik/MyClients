package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.session.data.GetSessionUsagesHistoryRequest
import ru.shafran.network.session.data.SessionUsageHistoryItem

interface SessionsUsageHistoryStore :
    Store<SessionsUsageHistoryStore.Intent, SessionsUsageHistoryStore.State, SessionsUsageHistoryStore.Label> {

    sealed class Intent {
        data class LoadHistory(
            val offset: Int = 30,
            val page: Int = 0,
            val companyId: CompanyId,
        ) : Intent()
    }

    sealed class State {

        object Empty : State()

        data class HistoryLoaded(
            val request: GetSessionUsagesHistoryRequest,
            val history: List<SessionUsageHistoryItem>,
        ) : State()

        object Loading : State()

        sealed class Error : State() {
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }

    }

    sealed class Label {}

}