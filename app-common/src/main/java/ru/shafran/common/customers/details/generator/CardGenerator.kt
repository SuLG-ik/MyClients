package ru.shafran.common.customers.details.generator

import ru.shafran.common.customers.details.edit.CustomerEditor
import ru.shafran.network.customers.data.CreateCustomerRequest
import ru.shafran.network.customers.data.EditableCustomerData

interface CardGenerator {

    val data: EditableCustomerData?

    val editor: CustomerEditor

    val onGenerate: (CreateCustomerRequest) -> Unit

}