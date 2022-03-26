package ru.shafran.common.details.generator

import ru.shafran.common.details.edit.CustomerEditor
import ru.shafran.common.details.edit.CustomerEditorComponent
import ru.shafran.network.customers.data.CreateCustomerRequest
import ru.shafran.network.customers.data.CustomerData

class CardGeneratorComponent(
    private val onGenerate: (CreateCustomerRequest) -> Unit,
) : CardGenerator {

    override val editor: CustomerEditor =
        CustomerEditorComponent(onEdit = this::onEdit)

    override fun onGenerate(request: CreateCustomerRequest) {
        onGenerate.invoke(request)
    }

    private fun onEdit(data: CustomerData) {
        onGenerate(CreateCustomerRequest(data))
    }

}