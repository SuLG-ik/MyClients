package ru.shafran.common.services

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.services.list.ServicesListHost

interface Services {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Child {
        class ServicesList(
            val component: ServicesListHost,
        ) : Child()
    }

    sealed class Configuration : Parcelable {

        @Parcelize
        object ServicesList: Configuration()

    }

}