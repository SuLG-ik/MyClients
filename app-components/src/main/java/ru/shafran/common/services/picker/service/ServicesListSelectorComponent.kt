package ru.shafran.common.services.picker.service

import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service

class ServicesListSelectorComponent(
    override val services: List<Service>,
    override val selectedConfiguration: ConfiguredService?,
    private val onSelect: (Service) -> Unit,
) : ServicesListSelector {

    override fun onSelect(service: Service) {
        onSelect.invoke(service)
    }
}