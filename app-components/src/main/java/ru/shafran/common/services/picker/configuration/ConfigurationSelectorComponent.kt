package ru.shafran.common.services.picker.configuration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

class ConfigurationSelectorComponent(
    componentContext: ComponentContext,
    service: Service,
    selectedConfiguration: ConfiguredService?,
    private val onSelect: (ConfiguredService) -> Unit,
    private val onBack: () -> Unit,
) : ConfigurationSelector, ComponentContext by componentContext {

    private val router =
        router<ConfigurationSelector.Configuration, ConfigurationSelector.Child>(
            initialConfiguration = ConfigurationSelector.Configuration.ConfigurationsList(service,
                selectedConfiguration),
            childFactory = this::createChild
        )

    override val routerState: Value<RouterState<ConfigurationSelector.Configuration, ConfigurationSelector.Child>>
        get() = router.state

    private fun createChild(
        configuration: ConfigurationSelector.Configuration,
        componentContext: ComponentContext,
    ): ConfigurationSelector.Child {
        return when (configuration) {
            is ConfigurationSelector.Configuration.ConfigurationsList ->
                configuration.create(componentContext)
            is ConfigurationSelector.Configuration.Loading ->
                configuration.create(componentContext)
        }
    }

    private fun ConfigurationSelector.Configuration.ConfigurationsList.create(componentContext: ComponentContext): ConfigurationSelector.Child {
        return ConfigurationSelector.Child.ConfigurationsList(
            ConfigurationsListSelectorComponent(
                service = service,
                selectedConfiguration = selectedConfiguration,
                onBack = onBack,
                onSelect = onSelect,
            )
        )
    }


    private fun ConfigurationSelector.Configuration.Loading.create(componentContext: ComponentContext): ConfigurationSelector.Child {
        return ConfigurationSelector.Child.Loading(LoadingComponent(R.string.services_loading))
    }


}