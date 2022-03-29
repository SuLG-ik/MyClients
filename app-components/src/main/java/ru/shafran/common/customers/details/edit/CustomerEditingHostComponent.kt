package ru.shafran.common.customers.details.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.stores
import ru.shafran.network.customers.CustomerEditingStore
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.CustomerData
import ru.shafran.network.customers.data.EditCustomerRequest
import ru.shafran.network.utils.reduceLabels
import ru.shafran.network.utils.reduceStates

class CustomerEditingHostComponent(
    componentContext: ComponentContext,
    private val customerId: String,
    private val onBack: () -> Unit,
    private val onBackAndUpdate: () -> Unit,
) : CustomerEditingHost, ComponentContext by componentContext {

    private val store by stores<CustomerEditingStore>()

    private val router = router<CustomerEditingHost.Configuration, CustomerEditingHost.Child>(
        initialConfiguration = CustomerEditingHost.Configuration.Loading,
        childFactory = this::createChild,
    )

    private fun createChild(
        configuration: CustomerEditingHost.Configuration,
        componentContext: ComponentContext,
    ): CustomerEditingHost.Child {
        return when (configuration) {
            is CustomerEditingHost.Configuration.Activating ->
                configuration.create()
            is CustomerEditingHost.Configuration.Editing ->
                configuration.create()
            is CustomerEditingHost.Configuration.Loading ->
                configuration.create()
        }
    }

    private fun CustomerEditingHost.Configuration.Activating.create(): CustomerEditingHost.Child.Activating {
        return CustomerEditingHost.Child.Activating(
            CustomerActivatingComponent(
                customer = customer,
                onBack = onBack,
                onEdit = this@CustomerEditingHostComponent::onEdit
            )
        )
    }

    private fun CustomerEditingHost.Configuration.Editing.create(): CustomerEditingHost.Child.Editing {
        return CustomerEditingHost.Child.Editing(
            CustomerEditingComponent(
                customer = customer,
                onEdit = this@CustomerEditingHostComponent::onEdit,
                onBack = onBack,
            )
        )
    }

    private fun CustomerEditingHost.Configuration.Loading.create(): CustomerEditingHost.Child.Loading {
        return CustomerEditingHost.Child.Loading(
            LoadingComponent(
                R.string.customers_editing_loading_message
            )
        )
    }


    private fun onEdit(request: CustomerData) {
        store.accept(CustomerEditingStore.Intent.Edit(
            EditCustomerRequest(
                customerId = customerId,
                data = request,
            ))
        )
    }

    override val routerState: Value<RouterState<CustomerEditingHost.Configuration, CustomerEditingHost.Child>>
        get() = router.state



    private fun reduceStates(state: CustomerEditingStore.State) {
        when (state) {
            is CustomerEditingStore.State.DetailsLoaded -> state.reduce()
            is CustomerEditingStore.State.Loading -> state.reduce()
            is CustomerEditingStore.State.Empty ->
                store.accept(CustomerEditingStore.Intent.LoadDetails(customerId))
        }
    }

    private fun reduceLabels(label: CustomerEditingStore.Label) {
        when (label) {
            CustomerEditingStore.Label.EditCompleted -> onBackAndUpdate()
        }
    }


    private fun CustomerEditingStore.State.DetailsLoaded.reduce() {
        when (val customer = customer) {
            is Customer.ActivatedCustomer ->
                router.navigate {
                    listOf(CustomerEditingHost.Configuration.Editing(
                        customer = customer,
                    ))
                }

            is Customer.InactivatedCustomer ->
                router.navigate {
                    listOf(CustomerEditingHost.Configuration.Activating(
                        customer = customer,
                    ))
                }
        }
    }

    private fun CustomerEditingStore.State.Loading.reduce() {
        router.navigate { listOf(CustomerEditingHost.Configuration.Loading) }
    }

    init {
        store.reduceStates(this, this::reduceStates)
        store.reduceLabels(this, this::reduceLabels)
    }

}