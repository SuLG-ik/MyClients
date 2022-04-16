package ru.shafran.common.services.details.edit

import ru.shafran.common.services.details.create.ServiceConfigurationCreator
import ru.shafran.common.services.details.create.ServiceConfigurationCreating
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.services.data.Service

class ServiceConfigurationCreatingComponent(
    service: Service,
    override val onBack: () -> Unit,
    onApply: (CreateConfigurationRequestData) -> Unit,
) : ServiceConfigurationCreating {
    override val editor: ServiceConfigurationCreator =
        ServiceConfigurationCreatorComponent(service, onApply = onApply)
}