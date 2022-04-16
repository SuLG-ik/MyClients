package ru.shafran.common.services.details.create

import ru.shafran.common.services.details.edit.ServiceEditor

interface ServiceCreating {
    val editor: ServiceEditor
}