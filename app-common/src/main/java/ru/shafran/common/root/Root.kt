package ru.shafran.common.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface Root {

    val routerState: Value<RouterState<Configuration, Child<Any?>>>

    fun onNavigate(configuration: Configuration)

    sealed class Child<out T> {

        abstract val component: T

        class CustomerScanner(
            override val component: ru.shafran.common.scanner.CustomerScanner,
        ) :
            Child<ru.shafran.common.scanner.CustomerScanner>()

        class Services(
            override val component: ru.shafran.common.services.Services,
        ) : Child<ru.shafran.common.services.Services>()

    }

    sealed class Configuration : Parcelable {

        @Parcelize
        object Services : Configuration()

        @Parcelize
        object CustomerScanner : Configuration()

    }

}