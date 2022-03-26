package ru.shafran.common.services.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.common.services.details.host.ServicesDetailsHost
import ru.shafran.common.services.details.info.ServiceInfoHostComponent
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.services.data.Service

class ServicesDetailsHostComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, ServicesDetailsHost {

    val router = router<ServicesDetailsHost.Configuration, ServicesDetailsHost.Child<Any?>>(
        initialConfiguration = ServicesDetailsHost.Configuration.Hidden,
        childFactory = this::createChild
    )

    private fun createChild(
        configuration: ServicesDetailsHost.Configuration,
        componentContext: ComponentContext,
    ): ServicesDetailsHost.Child<Any?> {
        return when (configuration) {
            is ServicesDetailsHost.Configuration.Hidden -> ServicesDetailsHost.Child.Hidden
            is ServicesDetailsHost.Configuration.ServiceInfoByData ->
                ServicesDetailsHost.Child.ServiceInfo(
                    ServiceInfoHostComponent(service = configuration.service,
                        componentContext = componentContext)
                )
            is ServicesDetailsHost.Configuration.ServiceInfoById ->
                ServicesDetailsHost.Child.ServiceInfo(
                    ServiceInfoHostComponent(serviceId = configuration.serviceId,
                        componentContext = componentContext)
                )
        }
    }

    override fun onShowServiceDetails(service: Service) {
        router.bringToFront(ServicesDetailsHost.Configuration.ServiceInfoByData(service))
    }

    override fun onShowServiceDetails(serviceId: String) {
        router.bringToFront(ServicesDetailsHost.Configuration.ServiceInfoById(serviceId))
    }

    override fun onHide() {
        router.replaceAll(ServicesDetailsHost.Configuration.Hidden)
    }


    override val isShown: Value<Boolean>
        get() = routerState.map { it.activeChild.configuration !is ServicesDetailsHost.Configuration.Hidden }

    override val routerState: Value<RouterState<ServicesDetailsHost.Configuration, ServicesDetailsHost.Child<Any?>>>
        get() = router.state


}