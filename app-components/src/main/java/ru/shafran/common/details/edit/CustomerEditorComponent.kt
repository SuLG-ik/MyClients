package ru.shafran.common.details.edit

import ru.shafran.network.customers.data.CustomerData

class CustomerEditorComponent(
    private val onBack: () -> Unit,
    private val onEdit: (CustomerData) -> Unit,
    override val data: CustomerData? = null,
) : CustomerEditor {

    override fun onBack() {
        onBack.invoke()
    }

    override fun onEdit(data: CustomerData) {
        onEdit.invoke(data)
    }

}