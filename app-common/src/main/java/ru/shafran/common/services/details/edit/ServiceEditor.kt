package ru.shafran.common.services.details.edit

import ru.shafran.network.services.data.EditableServiceData

interface ServiceEditor {

    val data: EditableServiceData?

    val onEdit: (EditableServiceData) -> Unit

}