package ru.shafran.common.services.details.edit

import ru.shafran.network.services.data.EditableServiceData

class ServiceEditingComponent(
    request: EditableServiceData,
    override val onBack: () -> Unit,
    private val onEdit: (EditableServiceData) -> Unit,
) : ServiceEditing {
    override val editor: ServiceEditor = ServiceEditorComponent(
        request,
        onEdit = { onEdit(it) }
    )

}