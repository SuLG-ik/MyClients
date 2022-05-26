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
import ru.shafran.network.companies.data.Company

class CompanyTargetApplicationComponent(
    componentContext: ComponentContext,
    private val onOpenSettings: () -> Unit,
    private val share: Share,
    override val company: Company,
) : CompanyTargetApplication, ComponentContext by componentContext {

    private val router =
        router<CompanyTargetApplication.Configuration, CompanyTargetApplication.Child<Any?>>(
            initialConfiguration = CompanyTargetApplication.Configuration.CustomerScanner,
            handleBackButton = true,
            childFactory = this::createChild,
            key = "root_router",
        )

    private fun createChild(
        configuration: CompanyTargetApplication.Configuration,
        componentContext: ComponentContext,
    ): CompanyTargetApplication.Child<Any?> {
        return when (configuration) {
            CompanyTargetApplication.Configuration.Services -> createServicesChild(componentContext)
            CompanyTargetApplication.Configuration.CustomerScanner -> createScannerChild(
                componentContext)
            CompanyTargetApplication.Configuration.Customers -> createCustomersChild(
                componentContext)
            CompanyTargetApplication.Configuration.Employees -> createEmployeesChild(
                componentContext)
        }
    }

    private fun createCustomersChild(componentContext: ComponentContext): CompanyTargetApplication.Child<Any?> {
        return CompanyTargetApplication.Child.Customers(
            CustomersComponent(
                componentContext = componentContext,
                company = company,
                share = share
            )
        )
    }

    private fun createServicesChild(
        componentContext: ComponentContext,
    ): CompanyTargetApplication.Child<Any?> {
        return CompanyTargetApplication.Child.Services(
            ServicesComponent(
                componentContext,
                company = company,
            )
        )
    }

    private fun createScannerChild(
        componentContext: ComponentContext,
    ): CompanyTargetApplication.Child<Any?> {
        return CompanyTargetApplication.Child.CustomerScanner(
            component = CustomerScannerComponent(
                componentContext = componentContext,
                onOpenSettings = onOpenSettings,
                share = share,
                company = company,
            )
        )
    }

    private fun createEmployeesChild(
        componentContext: ComponentContext,
    ): CompanyTargetApplication.Child<Any?> {
        return CompanyTargetApplication.Child.Employees(
            EmployeesComponent(
                componentContext = componentContext,
                company = company,
            )
        )
    }

    override val routerState: Value<RouterState<CompanyTargetApplication.Configuration, CompanyTargetApplication.Child<Any?>>> =
        router.state


    override fun onNavigate(configuration: CompanyTargetApplication.Configuration) {
        router.bringToFront(configuration)
    }


}