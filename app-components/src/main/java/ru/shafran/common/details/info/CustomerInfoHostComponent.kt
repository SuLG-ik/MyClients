package ru.shafran.common.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.replaceAll
import ru.shafran.common.utils.stores
import ru.shafran.network.customers.CustomerInfoStore
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.utils.reduceStates

class CustomerInfoHostComponent(
    componentContext: ComponentContext,
    private val customerToken: String,
    private val onBack: () -> Unit,
    private val onEdit: (Customer) -> Unit,
    private val onActivateSession: (Customer) -> Unit,
) : CustomerInfoHost, ComponentContext by componentContext {

    private val store by stores<CustomerInfoStore>()

    private val router = router<CustomerInfoHost.Configuration, CustomerInfoHost.Child>(
        initialConfiguration = CustomerInfoHost.Configuration.Loading,
        childFactory = this::createChild,
    )

    private fun createChild(
        configuration: CustomerInfoHost.Configuration,
        componentContext: ComponentContext,
    ): CustomerInfoHost.Child {
        return when (configuration) {
            is CustomerInfoHost.Configuration.ActivatedLoaded ->
                configuration.create()
            is CustomerInfoHost.Configuration.InactivatedLoaded ->
                configuration.create()
            is CustomerInfoHost.Configuration.ActivatedPreloaded ->
                configuration.create()
            is CustomerInfoHost.Configuration.Loading ->
                configuration.create()
        }
    }

    private fun CustomerInfoHost.Configuration.ActivatedLoaded.create(): CustomerInfoHost.Child.Loaded {
        return CustomerInfoHost.Child.Loaded(
            LoadedCustomerInfoComponent(
                customer = customer,
                history = history,
                onActivateSession = { onActivateSession(customer) },
                onBack = onBack,
                onEdit = { onEdit(customer) },
            )
        )
    }

    private fun CustomerInfoHost.Configuration.ActivatedPreloaded.create(): CustomerInfoHost.Child.Preloaded {
        return CustomerInfoHost.Child.Preloaded(
            PreloadedCustomerInfoComponent(
                customer = customer,
                onBack = onBack,
            )
        )
    }

    private fun CustomerInfoHost.Configuration.InactivatedLoaded.create(): CustomerInfoHost.Child.Inactivated {
        return CustomerInfoHost.Child.Inactivated(
            InactivatedCustomerInfoComponent(
                customer = customer,
                onBack = onBack,
                onEdit = { onEdit(customer) }
            )
        )
    }

    private fun CustomerInfoHost.Configuration.Loading.create(): CustomerInfoHost.Child.Loading {
        return CustomerInfoHost.Child.Loading(
            LoadingComponent(R.string.customers_info_loading_message)
        )
    }

    override val routerState: Value<RouterState<CustomerInfoHost.Configuration, CustomerInfoHost.Child>>
        get() = router.state

    init {
        store.reduceStates(this, this::reduceStates)
        lifecycle.doOnCreate {
            store.accept(CustomerInfoStore.Intent.LoadCustomerWithToken(customerToken))
        }
    }

    private fun reduceStates(state: CustomerInfoStore.State) {
        when (state) {
            is CustomerInfoStore.State.Loading ->
                state.reduce()
            is CustomerInfoStore.State.CustomerInactivatedLoaded ->
                state.reduce()
            is CustomerInfoStore.State.CustomerActivatedPreloaded ->
                state.reduce()
            is CustomerInfoStore.State.CustomerActivatedLoaded ->
                state.reduce()
        }
    }

    private fun CustomerInfoStore.State.CustomerInactivatedLoaded.reduce() {
        router.replaceAll(
            CustomerInfoHost.Configuration.InactivatedLoaded(
                customer = customer
            )
        )
    }

    private fun CustomerInfoStore.State.CustomerActivatedPreloaded.reduce() {
        router.replaceAll(
            CustomerInfoHost.Configuration.ActivatedPreloaded(
                customer = customer,
            )
        )
    }

    private fun CustomerInfoStore.State.CustomerActivatedLoaded.reduce() {
        router.replaceAll(
            CustomerInfoHost.Configuration.ActivatedLoaded(
                customer = customer,
                history = history,
            )
        )
    }


    private fun CustomerInfoStore.State.Loading.reduce() {
        router.replaceAll(CustomerInfoHost.Configuration.Loading)
    }

}