package ru.shafran.common.services.picker

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.common.services.picker.configuration.ConfigurationSelectorComponent
import ru.shafran.common.services.picker.service.ServiceSelectorComponent
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

class ConfiguredServicePickerComponent(componentContext: ComponentContext) :
    ConfiguredServicePicker, ComponentContext by componentContext {

    private val instance = instanceKeeper.getOrCreate { Instance() }

    override val selectedConfiguration: MutableStateFlow<ConfiguredService?> =
        instance.selectedConfiguration

    override val isPicking: MutableStateFlow<Boolean> = instance.isPicking

    private val router =
        router<ConfiguredServicePicker.Configuration, ConfiguredServicePicker.Child>(
            initialConfiguration = ConfiguredServicePicker.Configuration.ServicesSelector(
                selectedConfiguration.value),
            childFactory = this::createChild
        )

    override val routerState: Value<RouterState<ConfiguredServicePicker.Configuration, ConfiguredServicePicker.Child>>
        get() = router.state

    private fun createChild(
        configuration: ConfiguredServicePicker.Configuration,
        componentContext: ComponentContext,
    ): ConfiguredServicePicker.Child {
        return when (configuration) {
            is ConfiguredServicePicker.Configuration.ConfigurationSelector ->
                configuration.create(componentContext)
            is ConfiguredServicePicker.Configuration.ServicesSelector ->
                configuration.create(componentContext)
        }
    }

    private fun ConfiguredServicePicker.Configuration.ConfigurationSelector.create(componentContext: ComponentContext): ConfiguredServicePicker.Child {
        return ConfiguredServicePicker.Child.ConfigurationSelector(
            ConfigurationSelectorComponent(
                componentContext = componentContext,
                service = service,
                selectedConfiguration = selectedConfiguration,
                onSelect = this@ConfiguredServicePickerComponent::onConfiguredServiceSelect,
                onBack = this@ConfiguredServicePickerComponent::onBack,
            )
        )
    }

    private fun onBack() {
        router.pop()
    }

    private fun onConfiguredServiceSelect(configuredService: ConfiguredService) {
        onTogglePicking(false)
        selectedConfiguration.value = configuredService
    }

    private fun onServiceSelect(service: Service) {
        router.push(ConfiguredServicePicker.Configuration.ConfigurationSelector(service))
    }

    private fun ConfiguredServicePicker.Configuration.ServicesSelector.create(componentContext: ComponentContext): ConfiguredServicePicker.Child {
        return ConfiguredServicePicker.Child.ServiceSelector(
            ServiceSelectorComponent(
                componentContext = componentContext,
                selectedConfiguration = selectedConfiguration,
                onSelect = this@ConfiguredServicePickerComponent::onServiceSelect,
            )
        )
    }


    override fun onTogglePicking(isPicking: Boolean) {
        this.isPicking.value = isPicking
    }

    private class Instance : InstanceKeeper.Instance {

        val selectedConfiguration: MutableStateFlow<ConfiguredService?> = MutableStateFlow(null)

        val isPicking: MutableStateFlow<Boolean> = MutableStateFlow(false)

        override fun onDestroy() {}
    }

}