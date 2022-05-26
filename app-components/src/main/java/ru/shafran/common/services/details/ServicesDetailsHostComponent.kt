package ru.shafran.common.services.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.common.services.details.create.ServiceConfigurationCreatingHostComponent
import ru.shafran.common.services.details.create.ServiceCreatingHostComponent
import ru.shafran.common.services.details.edit.ServiceEditingHostComponent
import ru.shafran.common.services.details.host.ServicesDetailsHost
import ru.shafran.common.services.details.info.ServiceInfoHostComponent
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.companies.data.Company
import ru.shafran.network.services.data.Service

class ServicesDetailsHostComponent(
    componentContext: ComponentContext,
    private val company: Company,
    private val onUpdateList: () -> Unit = {},
) : ComponentContext by componentContext, ServicesDetailsHost {

    private fun createChild(
        configuration: ServicesDetailsHost.Configuration,
        componentContext: ComponentContext,
    ): ServicesDetailsHost.Child<Any?> {
        return when (configuration) {
            is ServicesDetailsHost.Configuration.Hidden -> ServicesDetailsHost.Child.Hidden
            is ServicesDetailsHost.Configuration.ServiceInfoByData ->
                ServicesDetailsHost.Child.ServiceInfo(
                    ServiceInfoHostComponent(
                        service = configuration.service,
                        componentContext = componentContext,
                        onEdit = onEditService,
                        onCreateConfiguration = onCreateConfiguration,
                    )
                )
            is ServicesDetailsHost.Configuration.ServiceInfoById ->
                ServicesDetailsHost.Child.ServiceInfo(
                    ServiceInfoHostComponent(
                        serviceId = configuration.serviceId,
                        componentContext = componentContext,
                        onEdit = onEditService,
                        onCreateConfiguration = onCreateConfiguration,
                    )
                )
            is ServicesDetailsHost.Configuration.CreateService ->
                ServicesDetailsHost.Child.CreateService(
                    ServiceCreatingHostComponent(
                        componentContext = componentContext,
                        onCreated = onShowServiceDetails,
                        onUpdateList = onUpdateList,
                        company = company,
                    )
                )
            is ServicesDetailsHost.Configuration.EditService -> ServicesDetailsHost.Child.EditService(
                ServiceEditingHostComponent(
                    componentContext = componentContext,
                    service = configuration.service,
                    onBack = onBack,
                    onEdited = onShowServiceDetails,
                    onUpdateList = onUpdateList,
                )
            )
            is ServicesDetailsHost.Configuration.CreateConfiguration -> ServicesDetailsHost.Child.CreateConfiguration(
                ServiceConfigurationCreatingHostComponent(
                    componentContext,
                    service = configuration.service,
                    onEdited = onShowServiceDetails,
                    onBack = onBack,
                    onUpdateList = onUpdateList,
                )
            )
            is ServicesDetailsHost.Configuration.EditConfiguration -> TODO()
        }
    }

    override val onCreateConfiguration: (service: Service) -> Unit = { service ->
        router.bringToFront(ServicesDetailsHost.Configuration.CreateConfiguration(service))
    }

    override val onEditService: (service: Service) -> Unit = { service ->
        router.bringToFront(ServicesDetailsHost.Configuration.EditService(service))
    }


    override val onShowServiceDetails: (service: Service) -> Unit = { service ->
        router.bringToFront(ServicesDetailsHost.Configuration.ServiceInfoByData(service))
    }

    override val onShowServiceDetailsById: (serviceId: String) -> Unit = { serviceId ->
        router.bringToFront(ServicesDetailsHost.Configuration.ServiceInfoById(serviceId))
    }

    override val onCreateService: () -> Unit = {
        router.bringToFront(ServicesDetailsHost.Configuration.CreateService)
    }

    override val onHide: () -> Unit = {
        router.replaceAll(ServicesDetailsHost.Configuration.Hidden)
    }

    private val onBack: () -> Unit = {
        if (router.state.value.backStack.size > 1)
            router.pop()
        else
            onHide()
    }


    override val isShown: Value<Boolean>
        get() = routerState.map { it.activeChild.configuration !is ServicesDetailsHost.Configuration.Hidden }

    override val routerState: Value<RouterState<ServicesDetailsHost.Configuration, ServicesDetailsHost.Child<Any?>>>
        get() = router.state

    val router = router<ServicesDetailsHost.Configuration, ServicesDetailsHost.Child<Any?>>(
        initialConfiguration = ServicesDetailsHost.Configuration.Hidden,
        childFactory = this::createChild
    )

}