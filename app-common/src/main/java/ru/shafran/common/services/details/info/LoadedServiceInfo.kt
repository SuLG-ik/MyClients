package ru.shafran.common.services.details.info

import ru.shafran.network.services.data.Service

interface LoadedServiceInfo {

    val service: Service

    val onEdit: () -> Unit

    val onCreateConfiguration: () -> Unit

}