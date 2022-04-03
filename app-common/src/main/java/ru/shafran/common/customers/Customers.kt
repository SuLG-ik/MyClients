package ru.shafran.common.customers

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.sessions.history.SessionsHistoryListHost

interface Customers {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Child {
        class SessionsHistory(
            val component: SessionsHistoryListHost,
        ) : Child()
    }

    sealed class Configuration : Parcelable {

        @Parcelize
        object SessionsHistory : Configuration()

    }


}