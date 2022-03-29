package ru.shafran.common.customers.details.search

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import ru.shafran.network.customers.data.Customer

interface CustomerSearchByName {

    val router: Value<RouterState<FoundCustomerConfiguration, FoundCustomerChild>>

    fun onFind(customer: Customer.ActivatedCustomer)

    fun onSearch(name: String)

}