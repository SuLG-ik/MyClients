package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.CustomerData

class CustomerEditingComponent(
    override val customer: Customer.ActivatedCustomer,
    private val onBack: () -> Unit,
    onEdit: (CustomerData) -> Unit,
) : CustomerEditing {

    override val editor: CustomerEditor = CustomerEditorComponent(
        onEdit = onEdit,
        data = customer.data
    )

    override fun onBack() {
        onBack.invoke()
    }
}