package ru.shafran.common.services.picker.service

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Updatable
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
) : ServiceSelector, Updatable, ComponentContext by componentContext {

    override val onUpdate: (() -> Unit) = {
        store.accept(ServicesListStore.Intent.LoadServices())
    }
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
                configuration.create()
            is ServiceSelector.Configuration.ServicesList ->
                configuration.create()
            is ServiceSelector.Configuration.UnknownError ->
                configuration.create()
        }
    }

    private fun ServiceSelector.Configuration.UnknownError.create(): ServiceSelector.Child {
        return ServiceSelector.Child.Error(ErrorComponent(
            message = R.string.unknwon_error,
            icon = R.drawable.error,
            onContinue = onUpdate,
        ))
    }

    private fun ServiceSelector.Configuration.Loading.create(): ServiceSelector.Child {
        return ServiceSelector.Child.Loading(LoadingComponent(R.string.services_loading))
    }

    private fun ServiceSelector.Configuration.ServicesList.create(): ServiceSelector.Child {
        return ServiceSelector.Child.ServicesList(
            ServicesListSelectorComponent(
                services = services,
                selectedConfiguration = selectedConfiguration,
                onSelect = onSelect,
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
            is ServicesListStore.State.Error ->
                router.replaceAll(ServiceSelector.Configuration.UnknownError)
            is ServicesListStore.State.Empty -> onUpdate()
        }
    }

    private fun ServicesListStore.State.ServicesLoaded.reduce() {
        router.replaceAll(ServiceSelector.Configuration.ServicesList(
            services = services,
            selectedConfiguration = selectedConfiguration,
        ))
    }

}