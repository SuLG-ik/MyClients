package ru.shafran.common.details.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.network.customers.data.Customer

internal class CustomerDetailsHostComponent(
    componentContext: ComponentContext,
) : CustomerDetailsHost, ComponentContext by componentContext {


    private val childFactory: (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child =
        CustomerDetailsChildFactory(
            onBack = this::onBack,
            onEdit = this::onEdit,
            onActivateSession = this::onActivateSession,
        )

    private val router = router<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child>(
        initialConfiguration = CustomerDetailsHost.Configuration.Hidden,
        childFactory = childFactory,
    )

    private fun onEdit(customer: Customer) {
        router.push(CustomerDetailsHost.Configuration.EditCustomer(customer.id))
    }

    private fun onActivateSession(customer: Customer) {
        router.push(CustomerDetailsHost.Configuration.SessionActivation(customer.id))
    }

    private fun onBack() {
        router.pop()
    }

    override fun onHide() {
        router.navigate { listOf(CustomerDetailsHost.Configuration.Hidden) }
    }

    override fun onShowCustomer(token: String) {
        router.push(CustomerDetailsHost.Configuration.CustomerInfo(token))
    }

    override val isShown: Value<Boolean>
        get() = router.state.map { it.activeChild.configuration !is CustomerDetailsHost.Configuration.Hidden }

    override val routerState: Value<RouterState<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child>>
        get() = router.state

}