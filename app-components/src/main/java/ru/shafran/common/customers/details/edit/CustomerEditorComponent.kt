package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.CustomerData
import ru.shafran.network.customers.data.EditableCustomerData

class CustomerEditorComponent(
    override val onEdit: (EditableCustomerData) -> Unit,
    override val data: CustomerData? = null,
) : CustomerEditor