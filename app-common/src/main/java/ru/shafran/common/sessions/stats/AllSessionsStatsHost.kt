package ru.shafran.common.sessions.stats

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.session.data.SessionStats
import ru.shafran.network.utils.DatePeriod

interface AllSessionsStatsHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        object UnknownError : Configuration()

        @Parcelize
        class StatsLoaded(val period: DatePeriod, val stats: SessionStats) : Configuration()

    }

    sealed class Child {

        class Loading(val component: ru.shafran.common.loading.Loading) : Child()

        class Error(val component: ru.shafran.common.error.Error) : Child()

        class StatsLoaded(val component: AllSessionsStats) : Child()

    }

}