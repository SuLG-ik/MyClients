package ru.shafran.common.details.edit

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.CustomerData

class CustomerActivatingComponent(
    override val customer: Customer.InactivatedCustomer,
    private val onBack: () -> Unit,
    onEdit: (CustomerData) -> Unit,
) : CustomerActivating {

    override val editor: CustomerEditor = CustomerEditorComponent(
        onEdit = onEdit,
    )

    override fun onBack() {
        onBack.invoke()
    }
}