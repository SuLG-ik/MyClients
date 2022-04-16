package ru.shafran.common.services.details.create

import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.services.data.Service

interface ServiceConfigurationCreator {

    val service: Service

    val onApply: (CreateConfigurationRequestData) -> Unit

}