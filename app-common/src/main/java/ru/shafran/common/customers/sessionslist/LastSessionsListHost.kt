package ru.shafran.common.customers.sessionslist

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.customers.details.host.CustomerDetailsHost
import ru.shafran.network.session.data.Session

interface LastSessionsListHost {

    val customerDetails: CustomerDetailsHost

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class ServicesList(val list: List<Session>, val isLoading: Boolean = false) :
            Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        object UnknownError : Configuration()

    }


    sealed class Child {

        class LastSessionsList(
            val component: ru.shafran.common.customers.sessionslist.LastSessionsList,
        ) : Child()

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()
    }


}