package ru.shafran.common.customers.details.search

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.FoundCustomerItem

class CustomersListComponent(
    override val customers: List<FoundCustomerItem>,
    onSelect: (Customer.ActivatedCustomer) -> Unit,
) : FoundCustomersList {

    override val onSelect: (FoundCustomerItem) -> Unit = { onSelect.invoke(it.customer) }

}