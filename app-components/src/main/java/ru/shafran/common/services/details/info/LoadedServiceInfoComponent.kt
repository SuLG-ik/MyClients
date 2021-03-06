package ru.shafran.common.services.details.info

import ru.shafran.network.services.data.Service

class LoadedServiceInfoComponent(
    override val service: Service,
    override val onEdit: () -> Unit,
    override val onCreateConfiguration: () -> Unit,
) : LoadedServiceInfo