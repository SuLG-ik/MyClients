package ru.shafran.common.customers.details.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.activeChild
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.common.utils.Share
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

internal class CustomerDetailsHostComponent(
    componentContext: ComponentContext,
    share: Share,
) : CustomerDetailsHost, ComponentContext by componentContext {

    private val childFactory: (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child<Any?> =
        CustomerDetailsChildFactory(
            onBack = this::onBack,
            onEdit = this::onEdit,
            onUseSession = this::onUseSession,
            onActivateSession = this::onActivateSession,
            onBackAndUpdate = this::onBackAndUpdate,
            onProfile = this::onProfile,
            share = share,
            onShareCard = this::onShareCard
        )

    private val router = router<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child<Any?>>(
        initialConfiguration = CustomerDetailsHost.Configuration.Hidden,
        childFactory = childFactory,
    )

    private fun onProfile(customer: Customer.ActivatedCustomer) {
        router.bringToFront(CustomerDetailsHost.Configuration.CustomerInfoById(customer.id))
    }

    private fun onEdit(customer: Customer) {
        router.bringToFront(CustomerDetailsHost.Configuration.EditCustomer(customer.id))
    }

    private fun onActivateSession(customer: Customer.ActivatedCustomer) {
        router.bringToFront(CustomerDetailsHost.Configuration.SessionActivation(customer))
    }

    private fun onUseSession(session: Session) {
        router.bringToFront(CustomerDetailsHost.Configuration.SessionUse(session))
    }

    private fun onShareCard(cardToken: String, customer: Customer.ActivatedCustomer) {
        router.bringToFront(CustomerDetailsHost.Configuration.ShareCard(cardToken, customer))
    }

    private fun onBack() {
        if (router.state.value.backStack.size > 1)
            router.pop()
        else
            onHide()
    }

    private fun onBackAndUpdate() {
        onBack()
        (router.activeChild.instance.component as? Updatable)?.onUpdate()
    }

    override fun onHide() {
        router.replaceAll(CustomerDetailsHost.Configuration.Hidden)
    }

    override fun onGenerateCustomer() {
        router.bringToFront(CustomerDetailsHost.Configuration.GenerateCard())
    }

    override fun onSearch() {
        router.bringToFront(CustomerDetailsHost.Configuration.Search)
    }

    override fun onShowCustomer(token: String) {
        router.bringToFront(CustomerDetailsHost.Configuration.CustomerInfoByToken(token))
    }

    override val isShown: Value<Boolean>
        get() = router.state.map { it.activeChild.configuration !is CustomerDetailsHost.Configuration.Hidden }

    override val routerState: Value<RouterState<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child<Any?>>>
        get() = router.state

}