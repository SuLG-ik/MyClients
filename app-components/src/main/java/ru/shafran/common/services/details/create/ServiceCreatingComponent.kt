package ru.shafran.common.services.details.create

import ru.shafran.common.services.details.edit.ServiceEditor
import ru.shafran.common.services.details.edit.ServiceEditorComponent
import ru.shafran.network.services.data.EditableServiceData

class ServiceCreatingComponent(
    data: EditableServiceData?,
    private val onCreate: (request: EditableServiceData) -> Unit,
) : ServiceCreating {
    override val editor: ServiceEditor = ServiceEditorComponent(
        data = data,
        onEdit = { onCreate(it) }
    )
}