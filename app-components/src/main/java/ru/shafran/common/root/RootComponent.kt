package ru.shafran.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.customers.CustomersComponent
import ru.shafran.common.employees.EmployeesComponent
import ru.shafran.common.scanner.CustomerScannerComponent
import ru.shafran.common.services.ServicesComponent
import ru.shafran.common.utils.Share

class RootComponent(
    componentContext: ComponentContext,
    private val onOpenSettings: () -> Unit,
    private val share: Share,
) : Root, ComponentContext by componentContext {

    private val router = router<Root.Configuration, Root.Child<Any?>>(
        initialConfiguration = Root.Configuration.CustomerScanner,
        handleBackButton = true,
        childFactory = this::createChild,
        key = "root_router",
    )

    private fun createChild(
        configuration: Root.Configuration,
        componentContext: ComponentContext,
    ): Root.Child<Any?> {
        return when (configuration) {
            Root.Configuration.Services -> createServicesChild(componentContext)
            Root.Configuration.CustomerScanner -> createScannerChild(componentContext)
            Root.Configuration.Customers -> createCustomersChild(componentContext)
            Root.Configuration.Employees -> createEmployeesChild(componentContext)
        }
    }

    private fun createCustomersChild(componentContext: ComponentContext): Root.Child<Any?> {
        return Root.Child.Customers(CustomersComponent(componentContext, share))
    }

    private fun createServicesChild(
        componentContext: ComponentContext,
    ): Root.Child<Any?> {
        return Root.Child.Services(ServicesComponent(componentContext))
    }

    private fun createScannerChild(
        componentContext: ComponentContext,
    ): Root.Child<Any?> {
        return Root.Child.CustomerScanner(
            component = CustomerScannerComponent(
                componentContext = componentContext,
                onOpenSettings = onOpenSettings,
                share = share
            )
        )
    }

    private fun createEmployeesChild(
        componentContext: ComponentContext,
    ): Root.Child<Any?> {
        return Root.Child.Employees(
            EmployeesComponent(componentContext)
        )
    }

    override val routerState: Value<RouterState<Root.Configuration, Root.Child<Any?>>> =
        router.state


    override fun onNavigate(configuration: Root.Configuration) {
        router.bringToFront(configuration)
    }


}