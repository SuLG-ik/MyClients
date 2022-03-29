package ru.shafran.common.customers.details.search

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import ru.shafran.network.PhoneNumber

interface CustomerSearchByPhone {

    val routerState: Value<RouterState<FoundCustomerConfiguration, FoundCustomerChild>>

    val onSearch: (PhoneNumber) -> Unit

}