package ru.shafran.common.services.details.edit

import ru.shafran.common.services.details.create.ServiceConfigurationCreator
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.services.data.Service

class ServiceConfigurationCreatorComponent(
    override val service: Service,
    override val onApply: (configuration: CreateConfigurationRequestData) -> Unit,
) : ServiceConfigurationCreator