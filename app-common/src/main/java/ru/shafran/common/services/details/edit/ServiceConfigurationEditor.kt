package ru.shafran.common.services.details.edit

import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.EditConfigurationRequest

interface ServiceConfigurationEditor {

    val configuration: ConfiguredService

    val onApply: (configuration: EditConfigurationRequest) -> Unit

}