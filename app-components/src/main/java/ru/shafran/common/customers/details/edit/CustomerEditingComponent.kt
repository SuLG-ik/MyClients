package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.EditableCustomerData

class CustomerEditingComponent(
    override val customer: Customer.ActivatedCustomer,
    private val onBack: () -> Unit,
    onEdit: (EditableCustomerData) -> Unit,
) : CustomerEditing {

    override val editor: CustomerEditor = CustomerEditorComponent(
        onEdit = onEdit,
        data = customer.data
    )

    override fun onBack() {
        onBack.invoke()
    }
}