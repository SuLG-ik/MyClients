package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.EditableCustomerData

class CustomerActivatingComponent(
    override val customer: Customer.InactivatedCustomer,
    private val onBack: () -> Unit,
    onEdit: (EditableCustomerData) -> Unit,
) : CustomerActivating {

    override val editor: CustomerEditor = CustomerEditorComponent(
        onEdit = onEdit,
    )

    override fun onBack() {
        onBack.invoke()
    }
}