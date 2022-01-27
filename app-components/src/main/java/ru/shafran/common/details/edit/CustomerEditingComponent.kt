package ru.shafran.common.details.edit

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.CustomerData

class CustomerEditingComponent(
    onBack: () -> Unit,
    onEdit: (CustomerData) -> Unit,
    override val customer: Customer.ActivatedCustomer,
) : CustomerEditing {

    override val editor: CustomerEditor = CustomerEditorComponent(
        onBack = onBack,
        onEdit = onEdit,
        data = customer.data
    )

}