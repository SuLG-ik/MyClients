package ru.shafran.common.services.details.create

import ru.shafran.common.services.details.edit.ServiceEditor
import ru.shafran.common.services.details.edit.ServiceEditorComponent
import ru.shafran.network.services.data.CreateServiceRequest

class ServiceCreatingComponent(
    request: CreateServiceRequest?,
    private val onCreate: (request: CreateServiceRequest) -> Unit,
) : ServiceCreating {
    override val editor: ServiceEditor = ServiceEditorComponent(
        data = request?.data,
        onEdit = { onCreate(CreateServiceRequest(it)) }
    )
}