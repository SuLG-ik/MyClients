package ru.shafran.common.customers.details.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.PhoneNumber
import ru.shafran.network.customers.CustomerSearchStore
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.SearchCustomerByPhoneRequest
import ru.shafran.network.utils.reduceStates

class CustomerSearchByPhoneComponent(
    componentContext: ComponentContext,
    private val onFind: (Customer.ActivatedCustomer) -> Unit,
) : CustomerSearchByPhone, ComponentContext by componentContext {

    val store = getStore<CustomerSearchStore>()

    private val router =
        router<FoundCustomerConfiguration, FoundCustomerChild>(
            initialConfiguration = FoundCustomerConfiguration.EmptyInput,
            childFactory = this::createChild
        )

    private fun createChild(
        configuration: FoundCustomerConfiguration,
        componentContext: ComponentContext,
    ): FoundCustomerChild {
        return when (configuration) {
            is FoundCustomerConfiguration.CustomersList ->
                FoundCustomerChild.CustomersList(
                    CustomersListComponent(
                        customers = configuration.customers,
                        onSelect = onFind,
                    )
                )
            is FoundCustomerConfiguration.EmptyInput ->
                FoundCustomerChild.EmptyInput(EmptyInputComponent())
            is FoundCustomerConfiguration.Loading ->
                FoundCustomerChild.Loading(LoadingComponent(R.string.customers_info_error_customer_not_found_message))
            FoundCustomerConfiguration.UnknownError ->
                FoundCustomerChild.Error(ErrorComponent(
                    R.string.unknwon_error,
                    R.drawable.error,
                ))
        }
    }

    override val routerState: Value<RouterState<FoundCustomerConfiguration, FoundCustomerChild>>
        get() = router.state

    override val onSearch: (PhoneNumber) -> Unit = { phoneNumber ->
        store.accept(CustomerSearchStore.Intent.SearchCustomerByPhone(
            SearchCustomerByPhoneRequest(phoneNumber))
        )
    }

    private fun reduceStates(state: CustomerSearchStore.State) {
        when (state) {
            is CustomerSearchStore.State.Empty ->
                router.replaceAll(FoundCustomerConfiguration.EmptyInput)
            is CustomerSearchStore.State.Error.Unknown ->
                router.replaceAll(FoundCustomerConfiguration.UnknownError)
            is CustomerSearchStore.State.Loading ->
                router.replaceAll(FoundCustomerConfiguration.Loading)
            is CustomerSearchStore.State.SearchCompleted ->
                router.replaceAll(FoundCustomerConfiguration.CustomersList(state.searchResult))
        }
    }

    init {
        store.reduceStates(this, this::reduceStates)
    }


}