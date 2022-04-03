package ru.shafran.common.services.picker.service

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.replaceAll
import ru.shafran.common.utils.stores
import ru.shafran.network.services.ServicesListStore
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.reduceStates

class ServiceSelectorComponent(
    componentContext: ComponentContext,
    private val selectedConfiguration: ConfiguredService?,
    private val onSelect: (Service) -> Unit,
) : ServiceSelector, ComponentContext by componentContext {

    private val store by stores<ServicesListStore>()

    private val router = router<ServiceSelector.Configuration, ServiceSelector.Child>(
        initialConfiguration = ServiceSelector.Configuration.Loading,
        childFactory = this::createChild
    )

    override val routerState: Value<RouterState<ServiceSelector.Configuration, ServiceSelector.Child>>
        get() = router.state

    private fun createChild(
        configuration: ServiceSelector.Configuration,
        componentContext: ComponentContext,
    ): ServiceSelector.Child {
        return when (configuration) {
            is ServiceSelector.Configuration.Loading ->
                configuration.create(componentContext)
            is ServiceSelector.Configuration.ServicesList ->
                configuration.create(componentContext)

        }
    }

    private fun ServiceSelector.Configuration.Loading.create(componentContext: ComponentContext): ServiceSelector.Child {
        return ServiceSelector.Child.Loading(LoadingComponent(R.string.services_loading))
    }

    private fun ServiceSelector.Configuration.ServicesList.create(componentContext: ComponentContext): ServiceSelector.Child {
        return ServiceSelector.Child.ServicesList(
            ServicesListSelectorComponent(
                services = services,
                selectedConfiguration = selectedConfiguration,
                onSelect = onSelect
            )
        )
    }

    init {
        store.reduceStates(this, this::reduceStates)
    }

    private fun reduceStates(state: ServicesListStore.State) {
        when (state) {
            is ServicesListStore.State.Loading -> router.replaceAll(ServiceSelector.Configuration.Loading)
            is ServicesListStore.State.ServicesLoaded -> state.reduce()
            ServicesListStore.State.Error.ConnectionLost -> TODO()
            ServicesListStore.State.Error.Internal -> TODO()
            ServicesListStore.State.Error.Unknown -> TODO()
            is ServicesListStore.State.Empty ->
                store.accept(ServicesListStore.Intent.LoadServices())
        }
    }

    private fun ServicesListStore.State.ServicesLoaded.reduce() {
        router.replaceAll(ServiceSelector.Configuration.ServicesList(
            services, selectedConfiguration
        ))
    }

}