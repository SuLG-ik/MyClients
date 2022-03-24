package ru.shafran.common.services.picker

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

interface ConfiguredServicePicker {

    val selectedConfiguration: StateFlow<ConfiguredService?>

    val isPicking: MutableStateFlow<Boolean>

    val routerState: Value<RouterState<Configuration, Child>>

    fun onTogglePicking(isPicking: Boolean = !this.isPicking.value)

    sealed class Configuration : Parcelable {

        @Parcelize
        class ServicesSelector(
            val selectedConfiguration: ConfiguredService? = null,
        ) : Configuration()

        @Parcelize
        class ConfigurationSelector(
            val service: Service,
            val selectedConfiguration: ConfiguredService? = null,
        ) : Configuration()
    }

    sealed class Child {

        class ServiceSelector(
            val component: ru.shafran.common.services.picker.service.ServiceSelector,
        ) : Child()

        class ConfigurationSelector(
            val component: ru.shafran.common.services.picker.configuration.ConfigurationSelector,
        ) : Child()
    }

}