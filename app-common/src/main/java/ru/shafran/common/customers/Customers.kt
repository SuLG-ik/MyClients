package ru.shafran.common.customers

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.customers.sessionslist.LastSessionsListHost

interface Customers {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Child {
        class LastSessionsList(
            val component: LastSessionsListHost,
        ) : Child()
    }

    sealed class Configuration : Parcelable {

        @Parcelize
        object LastSessionsList : Configuration()

    }


}