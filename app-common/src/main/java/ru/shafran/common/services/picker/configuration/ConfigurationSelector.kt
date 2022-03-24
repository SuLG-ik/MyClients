package ru.shafran.common.services.picker.configuration

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

interface ConfigurationSelector {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        class ConfigurationsList(
            val service: Service,
            val selectedConfiguration: ConfiguredService? = null,
        ) : Configuration()

    }

    sealed class Child {

        class Loading(val component: ru.shafran.common.loading.Loading) : Child()

        class ConfigurationsList(
            val component: ConfigurationsListSelector,
        ) : Child()

    }

}