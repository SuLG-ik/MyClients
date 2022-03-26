package ru.shafran.common.services.list

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.services.details.host.ServicesDetailsHost
import ru.shafran.network.services.data.Service

interface ServicesListHost {

    val serviceDetails: ServicesDetailsHost

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Child {

        class ServicesList(
            val component: ru.shafran.common.services.list.ServicesList,
        ) : Child()

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()

    }

    sealed class Configuration : Parcelable {

        @Parcelize
        data class ServicesList(val services: List<Service>, val isLoading: Boolean = false) :
            Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        object UnknownError : Configuration()

    }

}