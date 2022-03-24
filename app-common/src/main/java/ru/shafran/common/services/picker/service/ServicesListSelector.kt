package ru.shafran.common.services.picker.service

import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

interface ServicesListSelector {

    val selectedConfiguration: ConfiguredService?

    val services: List<Service>

    fun onSelect(service: Service)

}