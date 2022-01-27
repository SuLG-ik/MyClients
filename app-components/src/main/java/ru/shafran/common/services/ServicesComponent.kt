package ru.shafran.common.services

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.services.list.ServicesListHostComponent

internal class ServicesComponent(componentContext: ComponentContext) : Services,
    ComponentContext by componentContext {

    private val router = router(
        initialConfiguration = Services.Configuration.ServicesList,
        key = "services_router",
        childFactory = this::createChild,
        handleBackButton = true,
    )

    private fun createChild(
        configuration: Services.Configuration,
        componentContext: ComponentContext,
    ): Services.Child {
        return when (configuration) {
            is Services.Configuration.ServicesList ->
                createServicesListChild(configuration, componentContext)
        }
    }

    private fun createServicesListChild(
        configuration: Services.Configuration.ServicesList,
        componentContext: ComponentContext,
    ): Services.Child {
        return Services.Child.ServicesList(
            ServicesListHostComponent(
                componentContext = componentContext,
            )
        )
    }

    override val routerState: Value<RouterState<Services.Configuration, Services.Child>>
        get() = router.state

}