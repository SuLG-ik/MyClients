package ru.shafran.common.services.details.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.services.EditServiceStore
import ru.shafran.network.services.data.EditServiceRequest
import ru.shafran.network.services.data.EditableServiceData
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class ServiceEditingHostComponent(
    componentContext: ComponentContext,
    val service: Service,
    onEdited: (Service) -> Unit,
    val onBack: () -> Unit,
    private val onUpdateList: () -> Unit,
) : ServiceEditingHost, ComponentContext by componentContext {


    private val onEdited: (Service) -> Unit = {
        onEdited(it)
        onUpdateList()
    }

    private val onUpdate: (EditableServiceData?) -> Unit = {
        store.accept(
            EditServiceStore.Intent.LoadDetails(
                it ?: EditableServiceData(
                    title = service.data.info.title,
                    description = service.data.info.description,
                )
            )
        )
    }

    private val onEdit: (EditableServiceData) -> Unit = {
        store.accept(EditServiceStore.Intent.EditService(EditServiceRequest(serviceId = service.id,
            it)))
    }

    private fun createChild(
        configuration: ServiceEditingHost.Configuration,
        componentContext: ComponentContext,
    ): ServiceEditingHost.Child {
        return when (configuration) {
            is ServiceEditingHost.Configuration.EditServiceLoading ->
                ServiceEditingHost.Child.Loading(
                    LoadingComponent(
                        R.string.services_editing,
                    )
                )
            is ServiceEditingHost.Configuration.EditService ->
                ServiceEditingHost.Child.EditService(
                    ServiceEditingComponent(
                        request = configuration.request,
                        onBack = onBack,
                        onEdit = onEdit,
                    )
                )
            is ServiceEditingHost.Configuration.UnknownError ->
                ServiceEditingHost.Child.Error(
                    ErrorComponent(
                        message = R.string.unknwon_error,
                        icon = R.drawable.error,
                        onContinue = { onUpdate(configuration.request.data) }
                    )
                )
        }
    }

    private fun reduceLabel(label: EditServiceStore.Label) {
        when (label) {
            is EditServiceStore.Label.OnServiceEdited ->
                onEdited(label.service)
        }
    }

    private fun reduceState(state: EditServiceStore.State) {
        when (state) {
            is EditServiceStore.State.EditService ->
                router.replaceAll(ServiceEditingHost.Configuration.EditService(state.request))
            is EditServiceStore.State.Error ->
                router.replaceAll(ServiceEditingHost.Configuration.UnknownError(state.request))
            is EditServiceStore.State.EditServiceLoading ->
                router.replaceAll(ServiceEditingHost.Configuration.EditServiceLoading(state.request))
            is EditServiceStore.State.Empty -> onUpdate(null)
        }
    }

    private val store = getStore<EditServiceStore>()
        .reduceStates(this, this::reduceState)
        .reduceLabels(this, this::reduceLabel)

    val router = router<ServiceEditingHost.Configuration, ServiceEditingHost.Child>(
        initialConfiguration = ServiceEditingHost.Configuration.EditService(
            EditableServiceData(
                title = service.data.info.title,
                description = service.data.info.description,
            )),
        childFactory = this::createChild
    )


    override val routerState: Value<RouterState<ServiceEditingHost.Configuration, ServiceEditingHost.Child>>
        get() = router.state


}