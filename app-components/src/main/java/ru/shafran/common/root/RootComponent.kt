package ru.shafran.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.scanner.CustomerScannerComponent
import ru.shafran.common.services.ServicesComponent

class RootComponent(
    componentContext: ComponentContext,
    private val onOpenSettings: () -> Unit,
) : Root, ComponentContext by componentContext {

    private val router = router<Root.Configuration, Root.Child>(
        initialConfiguration = Root.Configuration.CustomerScanner,
        handleBackButton = true,
        childFactory = this::createChild,
        key = "root_router",
    )

    private fun createChild(
        configuration: Root.Configuration,
        componentContext: ComponentContext,
    ): Root.Child {
        return when (configuration) {
            Root.Configuration.Services -> createServicesChild(componentContext)
            Root.Configuration.CustomerScanner -> createScannerChild(componentContext)
        }
    }

    private fun createServicesChild(
        componentContext: ComponentContext,
    ): Root.Child {
        return Root.Child.Services(ServicesComponent(componentContext))
    }

    private fun createScannerChild(
        componentContext: ComponentContext,
    ): Root.Child {
        return Root.Child.CustomerScanner(
            component = CustomerScannerComponent(
                componentContext = componentContext,
                onOpenSettings = onOpenSettings,
            )
        )
    }

    override val routerState: Value<RouterState<Root.Configuration, Root.Child>> = router.state


    override fun onNavigate(configuration: Root.Configuration) {
        router.bringToFront(configuration)
    }


}