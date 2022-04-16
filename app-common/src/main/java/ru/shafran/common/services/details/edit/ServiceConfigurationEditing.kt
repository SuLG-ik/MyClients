package ru.shafran.common.services.details.edit

import ru.shafran.common.services.details.create.ServiceConfigurationCreator

interface ServiceConfigurationEditing {
    val editor: ServiceConfigurationCreator
}