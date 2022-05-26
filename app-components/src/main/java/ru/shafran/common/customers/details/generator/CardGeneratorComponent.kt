package ru.shafran.common.customers.details.generator

import ru.shafran.common.customers.details.edit.CustomerEditor
import ru.shafran.common.customers.details.edit.CustomerEditorComponent
import ru.shafran.network.customers.data.EditableCustomerData

class CardGeneratorComponent(
    override val data: EditableCustomerData?,
    override val onGenerate: (EditableCustomerData) -> Unit,
) : CardGenerator {

    override val editor: CustomerEditor =
        CustomerEditorComponent(onEdit = onGenerate)


}