package ru.shafran.common.services.details.info

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.services.data.Service

interface ServiceInfoHost {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Empty : Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        data class ServiceLoaded(
            val service: Service,
        ) : Configuration()

    }


    sealed class Child {

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Loaded(
            val component: LoadedServiceInfo,
        ) : Child()

    }

}