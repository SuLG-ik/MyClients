package ru.shafran.common.details.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.activeChild
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

internal class CustomerDetailsHostComponent(
    componentContext: ComponentContext,
) : CustomerDetailsHost, ComponentContext by componentContext {


    private val childFactory: (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child<Any?> =
        CustomerDetailsChildFactory(
            onBack = this::onBack,
            onEdit = this::onEdit,
            onUseSession = this::onUseSession,
            onActivateSession = this::onActivateSession,
            onBackAndUpdate = this::onBackAndUpdate,
        )

    private val router = router<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child<Any?>>(
        initialConfiguration = CustomerDetailsHost.Configuration.Hidden,
        childFactory = childFactory,
    )

    private fun onEdit(customer: Customer) {
        router.push(CustomerDetailsHost.Configuration.EditCustomer(customer.id))
    }

    private fun onActivateSession(customer: Customer.ActivatedCustomer) {
        router.push(CustomerDetailsHost.Configuration.SessionActivation(customer))
    }

    private fun onUseSession(session: Session) {
        router.push(CustomerDetailsHost.Configuration.SessionUse(session))
    }

    private fun onBack() {
        if (router.state.value.backStack.size > 1)
            router.pop()
        else
            onHide()
    }

    private fun onBackAndUpdate() {
        router.pop()
        (router.activeChild.instance.component as? Updatable)?.onUpdate()
    }

    override fun onHide() {
        router.replaceAll(CustomerDetailsHost.Configuration.Hidden)
    }

    override fun onShowCustomer(token: String) {
        router.push(CustomerDetailsHost.Configuration.CustomerInfo(token))
    }

    override val isShown: Value<Boolean>
        get() = router.state.map { it.activeChild.configuration !is CustomerDetailsHost.Configuration.Hidden }

    override val routerState: Value<RouterState<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child<Any?>>>
        get() = router.state

}