package ru.shafran.common.services.picker.configuration

import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service
import ru.shafran.network.services.data.ServiceConfiguration

class ConfigurationsListSelectorComponent(
    override val service: Service,
    override val selectedConfiguration: ConfiguredService?,
    private val onSelect: (ConfiguredService) -> Unit,
    private val onBack: () -> Unit,
) : ConfigurationsListSelector {

    override fun onSelect(configuration: ServiceConfiguration) {
        onSelect.invoke(
            ConfiguredService(
                serviceId = service.id,
                info = service.data.info,
                image = service.data.image,
                configuration = configuration,
            )
        )
    }

    override fun onBack() {
        onBack.invoke()
    }
}