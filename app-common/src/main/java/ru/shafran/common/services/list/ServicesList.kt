package ru.shafran.common.services.list

import ru.shafran.network.services.data.Service

interface ServicesList {

    val services: List<Service>

    fun onLoading()

    fun onCreateService()

    fun onSelectService(service: Service)

}