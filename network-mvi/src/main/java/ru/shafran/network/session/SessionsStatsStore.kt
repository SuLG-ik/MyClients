package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.session.data.SessionStats
import ru.shafran.network.utils.DatePeriod
import java.time.LocalDate

interface SessionsStatsStore :
    Store<SessionsStatsStore.Intent, SessionsStatsStore.State, SessionsStatsStore.Label> {

    sealed class Intent {
        data class LoadStats(
            val companyId: CompanyId,
            val period: DatePeriod = DatePeriod(from = LocalDate.now().minusMonths(1),
                to = LocalDate.now()),
        ) : Intent()
    }

    sealed class State {

        object Empty : State()

        data class StatsLoaded(val period: DatePeriod, val stats: SessionStats) : State()

        class Loading : State()

        sealed class Error : State() {
            object Unknown : Error()
        }

    }

    sealed class Label {}

}