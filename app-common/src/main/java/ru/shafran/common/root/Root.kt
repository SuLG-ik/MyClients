package ru.shafran.common.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface Root {


    val routerState: Value<RouterState<Configuration, Child>>

    fun onNavigate(configuration: Configuration)

    sealed class Child {

        class CustomerScanner(val component: ru.shafran.common.scanner.CustomerScanner) : Child()
        class Services(val component: ru.shafran.common.services.Services) : Child()

    }

    sealed class Configuration : Parcelable {

        @Parcelize
        object Services : Configuration()

        @Parcelize
        object CustomerScanner : Configuration()

    }

}