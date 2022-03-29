package ru.shafran.common.customers.details.search

import ru.shafran.network.customers.data.FoundCustomerItem

interface FoundCustomersList {

    val customers: List<FoundCustomerItem>

    val onSelect: (FoundCustomerItem) -> Unit

}

interface EmptyInput {}