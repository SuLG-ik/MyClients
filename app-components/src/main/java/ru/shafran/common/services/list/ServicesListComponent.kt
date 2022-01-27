package ru.shafran.common.services.list

import ru.shafran.network.services.data.Service

internal class ServicesListComponent(
    override val services: List<Service>,
    private val onLoading: () -> Unit,
    private val onCreateService: () -> Unit,
    private val onSelectService: (Service) -> Unit,
) : ServicesList {

    override fun onLoading() {
        onLoading.invoke()
    }

    override fun onCreateService() {
        onCreateService.invoke()
    }

    override fun onSelectService(service: Service) {
        onSelectService.invoke(service)
    }
}