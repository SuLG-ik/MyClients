package ru.shafran.common.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.customers.CustomerInfoStore
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session
import ru.shafran.network.utils.reduceStates

class CustomerInfoHostComponent(
    componentContext: ComponentContext,
    private val customerToken: String,
    private val onBack: () -> Unit,
    private val onEdit: (Customer) -> Unit,
    private val onActivateSession: (Customer.ActivatedCustomer) -> Unit,
    private val onUseSession: (Session) -> Unit,
) : CustomerInfoHost, Updatable, ComponentContext by componentContext {

    private val store = getStore<CustomerInfoStore>()

    private val router = router<CustomerInfoHost.Configuration, CustomerInfoHost.Child>(
        initialConfiguration = CustomerInfoHost.Configuration.Empty,
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
            is CustomerInfoHost.Configuration.Empty ->
                configuration.create()
            is CustomerInfoHost.Configuration.Unknown ->
                CustomerInfoHost.Child.Error(ErrorComponent(R.string.customers_info_error_unknown_message,
                    R.drawable.warn,
                    onContinue = onBack))
            is CustomerInfoHost.Configuration.CardNotFound ->
                CustomerInfoHost.Child.Error(ErrorComponent(R.string.customers_info_error_card_not_found_message,
                    R.drawable.warn,
                    onContinue = onBack))
            is CustomerInfoHost.Configuration.IllegalCard ->
                CustomerInfoHost.Child.Error(ErrorComponent(R.string.customers_info_error_illegal_card_message,
                    R.drawable.warn,
                    onContinue = onBack))
            is CustomerInfoHost.Configuration.CustomerNotFound ->
                CustomerInfoHost.Child.Error(ErrorComponent(R.string.customers_info_error_customer_not_found_message,
                    R.drawable.warn,
                    onContinue = onBack))
        }
    }

    private fun CustomerInfoHost.Configuration.ActivatedLoaded.create(): CustomerInfoHost.Child.Loaded {
        return CustomerInfoHost.Child.Loaded(
            LoadedCustomerInfoComponent(
                customer = customer,
                history = history,
                onActivateSession = { onActivateSession(customer) },
                onBack = onBack,
                onUseSession = onUseSession,
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

    private fun CustomerInfoHost.Configuration.Empty.create(): CustomerInfoHost.Child.Loading {
        return CustomerInfoHost.Child.Loading(
            LoadingComponent(R.string.customers_info_loading_message)
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
            is CustomerInfoStore.State.Empty ->
                state.reduce()
            is CustomerInfoStore.State.Error ->
                state.reduce()
        }
    }

    private fun CustomerInfoStore.State.Empty.reduce() {
        router.replaceAll(
            CustomerInfoHost.Configuration.Empty
        )
        onUpdate()
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


    private fun CustomerInfoStore.State.Error.reduce() {
        when (this) {
            CustomerInfoStore.State.Error.CardNotFound ->
                router.replaceCurrent(CustomerInfoHost.Configuration.CardNotFound)
            CustomerInfoStore.State.Error.CustomerNotFound ->
                router.replaceCurrent(CustomerInfoHost.Configuration.CustomerNotFound)
            CustomerInfoStore.State.Error.IllegalCard ->
                router.replaceCurrent(CustomerInfoHost.Configuration.IllegalCard)
            CustomerInfoStore.State.Error.Unknown ->
                router.replaceCurrent(CustomerInfoHost.Configuration.Unknown)
        }
    }

    override fun onUpdate() {
        store.accept(CustomerInfoStore.Intent.LoadCustomerWithToken(customerToken))
    }

}