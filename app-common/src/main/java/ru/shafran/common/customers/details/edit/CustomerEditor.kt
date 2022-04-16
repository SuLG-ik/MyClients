package ru.shafran.common.customers.details.edit

import ru.shafran.network.customers.data.CustomerData
import ru.shafran.network.customers.data.EditableCustomerData

interface CustomerEditor {

    val data: CustomerData?

    val onEdit: (data: EditableCustomerData) -> Unit

}