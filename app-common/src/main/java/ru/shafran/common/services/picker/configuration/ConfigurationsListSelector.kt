package ru.shafran.common.services.picker.configuration

import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service
import ru.shafran.network.services.data.ServiceConfiguration

interface ConfigurationsListSelector {

    val selectedConfiguration: ConfiguredService?

    val service: Service

    fun onSelect(configuration: ServiceConfiguration)

    fun onBack()

}