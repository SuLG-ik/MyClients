package ru.shafran.common.services.details.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.services.details.edit.ServiceConfigurationCreatingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.services.CreateServiceConfigurationStore
import ru.shafran.network.services.data.CreateConfigurationRequest
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class ServiceConfigurationCreatingHostComponent(
    componentContext: ComponentContext,
    val service: Service,
    onEdited: (Service) -> Unit,
    val onBack: () -> Unit,
    private val onUpdateList: () -> Unit,
) : ServiceConfigurationCreatingHost, ComponentContext by componentContext {


    private val onEdited: (Service) -> Unit = {
        onEdited(it)
        onUpdateList()
    }

    private val onUpdate: (CreateConfigurationRequestData?) -> Unit = {
        store.accept(
            CreateServiceConfigurationStore.Intent.LoadDetails(it)
        )
    }

    private val onEdit: (CreateConfigurationRequestData) -> Unit = {
        store.accept(CreateServiceConfigurationStore.Intent.CreateConfiguration(
            CreateConfigurationRequest(serviceId = service.id, it))
        )
    }

    private fun createChild(
        configuration: ServiceConfigurationCreatingHost.Configuration,
        componentContext: ComponentContext,
    ): ServiceConfigurationCreatingHost.Child {
        return when (configuration) {
            is ServiceConfigurationCreatingHost.Configuration.CreateConfigurationLoading ->
                ServiceConfigurationCreatingHost.Child.Loading(
                    LoadingComponent(
                        R.string.services_editing,
                    )
                )
            is ServiceConfigurationCreatingHost.Configuration.CreateConfiguration ->
                ServiceConfigurationCreatingHost.Child.CreateConfiguration(
                    ServiceConfigurationCreatingComponent(
                        service = service,
                        onApply = onEdit,
                        onBack = onBack,
                    )
                )
            is ServiceConfigurationCreatingHost.Configuration.UnknownError ->
                ServiceConfigurationCreatingHost.Child.Error(
                    ErrorComponent(
                        message = R.string.unknwon_error,
                        icon = R.drawable.error,
                        onContinue = { onUpdate(configuration.request.data) }
                    )
                )
        }
    }

    private fun reduceLabel(label: CreateServiceConfigurationStore.Label) {
        when (label) {
            is CreateServiceConfigurationStore.Label.OnConfigurationCreated ->
                onEdited(label.service)
        }
    }

    private fun reduceState(state: CreateServiceConfigurationStore.State) {
        when (state) {
            is CreateServiceConfigurationStore.State.CreateConfiguration ->
                router.replaceAll(ServiceConfigurationCreatingHost.Configuration.CreateConfiguration(
                    service,
                    state.request))
            is CreateServiceConfigurationStore.State.Error ->
                router.replaceAll(ServiceConfigurationCreatingHost.Configuration.UnknownError(state.request))
            is CreateServiceConfigurationStore.State.CreateConfigurationLoading ->
                router.replaceAll(ServiceConfigurationCreatingHost.Configuration.CreateConfigurationLoading(
                    state.request))
            is CreateServiceConfigurationStore.State.Empty -> onUpdate(null)
        }
    }

    private val store = getStore<CreateServiceConfigurationStore>()
        .reduceStates(this, this::reduceState)
        .reduceLabels(this, this::reduceLabel)

    val router =
        router<ServiceConfigurationCreatingHost.Configuration, ServiceConfigurationCreatingHost.Child>(
            initialConfiguration = ServiceConfigurationCreatingHost.Configuration.CreateConfiguration(
                service = service,
                request = null,
            ),
            childFactory = this::createChild
        )


    override val routerState: Value<RouterState<ServiceConfigurationCreatingHost.Configuration, ServiceConfigurationCreatingHost.Child>>
        get() = router.state


}