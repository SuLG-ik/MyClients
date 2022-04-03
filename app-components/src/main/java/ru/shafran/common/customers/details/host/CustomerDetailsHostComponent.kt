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


    override val onShowCustomer: (customer: Customer.ActivatedCustomer) -> Unit = { customer ->
        router.bringToFront(CustomerDetailsHost.Configuration.CustomerInfoById(customer.id))
    }

    private val onEdit: (customer: Customer) -> Unit = { customer ->
        router.bringToFront(CustomerDetailsHost.Configuration.EditCustomer(customer.id))
    }

    private val onActivateSession: (customer: Customer.ActivatedCustomer) -> Unit = {
        router.bringToFront(CustomerDetailsHost.Configuration.SessionActivation(it))
    }

    private val onUseSession: (session: Session) -> Unit = { session ->
        router.bringToFront(CustomerDetailsHost.Configuration.SessionUse(session))
    }

    private val onShareCard: (cardToken: String, customer: Customer.ActivatedCustomer) -> Unit =
        { cardToken, customer ->
            router.bringToFront(CustomerDetailsHost.Configuration.ShareCard(cardToken, customer))
        }

    private val onBack: () -> Unit = {
        if (router.state.value.backStack.size > 1)
            router.pop()
        else
            onHide()
    }

    private val onBackAndUpdate: () -> Unit = {
        onBack()
        (router.activeChild.instance.component as? Updatable)?.onUpdate?.invoke()
    }

    override val onHide: () -> Unit = {
        router.replaceAll(CustomerDetailsHost.Configuration.Hidden)
    }

    override val onGenerateCustomer: () -> Unit = {
        router.bringToFront(CustomerDetailsHost.Configuration.GenerateCard())
    }

    override val onSearch: () -> Unit = {
        router.bringToFront(CustomerDetailsHost.Configuration.Search)
    }

    override val onShowCustomerByCardToken: (String) -> Unit = { token ->
        router.bringToFront(CustomerDetailsHost.Configuration.CustomerInfoByToken(token))
    }

    override val onShowCustomerById: (String) -> Unit = { id ->
        router.bringToFront(CustomerDetailsHost.Configuration.CustomerInfoById(id))
    }

    private val childFactory: (CustomerDetailsHost.Configuration, ComponentContext) -> CustomerDetailsHost.Child<Any?> =
        CustomerDetailsChildFactory(
            onBack = onBack,
            onEdit = onEdit,
            onUseSession = onUseSession,
            onActivateSession = onActivateSession,
            onBackAndUpdate = onBackAndUpdate,
            onProfile = onShowCustomer,
            share = share,
            onShareCard = onShareCard
        )


    private val router = router<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child<Any?>>(
        initialConfiguration = CustomerDetailsHost.Configuration.Hidden,
        childFactory = childFactory,
        key = "customer_details_router"
    )

    override val isShown: Value<Boolean>
        get() = router.state.map { it.activeChild.configuration !is CustomerDetailsHost.Configuration.Hidden }

    override val routerState: Value<RouterState<CustomerDetailsHost.Configuration, CustomerDetailsHost.Child<Any?>>>
        get() = router.state

}