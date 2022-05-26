package ru.shafran.common.services.details.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.companies.data.Company
import ru.shafran.network.services.CreateServiceStore
import ru.shafran.network.services.data.CreateServiceRequest
import ru.shafran.network.services.data.EditableServiceData
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class ServiceCreatingHostComponent(
    componentContext: ComponentContext,
    private val company: Company,
    onCreated: (Service) -> Unit,
    private val onUpdateList: () -> Unit = {},
) : ServiceCreatingHost, ComponentContext by componentContext {


    private val onCreated: (Service) -> Unit = {
        onCreated(it)
        onUpdateList()
    }

    private val onUpdate: (CreateServiceRequest?) -> Unit = {
        store.accept(CreateServiceStore.Intent.LoadDetails())
    }

    private val onCreate: (EditableServiceData) -> Unit =
        {
            store.accept(CreateServiceStore.Intent.CreateService(data = it, companyId = company.id))
        }

    private fun createChild(
        configuration: ServiceCreatingHost.Configuration,
        componentContext: ComponentContext,
    ): ServiceCreatingHost.Child {
        return when (configuration) {
            is ServiceCreatingHost.Configuration.CreateServiceLoading ->
                ServiceCreatingHost.Child.Loading(
                    LoadingComponent(
                        R.string.services_creating,
                    )
                )
            is ServiceCreatingHost.Configuration.CreateService ->
                ServiceCreatingHost.Child.CreateService(
                    ServiceCreatingComponent(
                        data = configuration.request?.data,
                        onCreate = onCreate,
                    )
                )
            is ServiceCreatingHost.Configuration.UnknownError ->
                ServiceCreatingHost.Child.Error(
                    ErrorComponent(
                        message = R.string.unknwon_error,
                        icon = R.drawable.error,
                        onContinue = { onUpdate(configuration.request) }
                    )
                )
        }
    }

    private fun reduceLabel(label: CreateServiceStore.Label) {
        when (label) {
            is CreateServiceStore.Label.OnServiceCreated ->
                onCreated(label.service)
        }
    }

    private fun reduceState(state: CreateServiceStore.State) {
        when (state) {
            is CreateServiceStore.State.CreateService ->
                router.replaceAll(ServiceCreatingHost.Configuration.CreateService(state.request))
            is CreateServiceStore.State.Error ->
                router.replaceAll(ServiceCreatingHost.Configuration.UnknownError(state.request))
            is CreateServiceStore.State.CreateServiceLoading ->
                router.replaceAll(ServiceCreatingHost.Configuration.CreateServiceLoading(state.request))
        }
    }

    private val store = getStore<CreateServiceStore>()
        .reduceStates(this, this::reduceState)
        .reduceLabels(this, this::reduceLabel)

    val router = router<ServiceCreatingHost.Configuration, ServiceCreatingHost.Child>(
        initialConfiguration = ServiceCreatingHost.Configuration.CreateService(null),
        childFactory = this::createChild
    )

    override val routerState: Value<RouterState<ServiceCreatingHost.Configuration, ServiceCreatingHost.Child>>
        get() = router.state

}