package ru.shafran.common.customers.details.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.shafran.network.customers.data.Customer

class CustomerSearchHostComponent(
    componentContext: ComponentContext,
    onFind: (Customer.ActivatedCustomer) -> Unit,
) : CustomerSearchHost, ComponentContext by componentContext {

    override val currentOption: Value<CustomerSearchHost.SearchOption> =
        MutableValue(CustomerSearchHost.SearchOption.PHONE)

    override val searchByName: CustomerSearchByName get() = TODO()

    override val searchByPhone: CustomerSearchByPhone = CustomerSearchByPhoneComponent(
        childContext("search_by_phone"),
        onFind = onFind,
    )

}