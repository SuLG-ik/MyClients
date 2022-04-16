package ru.shafran.common.services.details.edit

import ru.shafran.network.services.data.EditableServiceData

class ServiceEditorComponent(
    override val data: EditableServiceData?,
    override val onEdit: (EditableServiceData) -> Unit,
) : ServiceEditor