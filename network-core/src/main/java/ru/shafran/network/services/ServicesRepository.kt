package ru.shafran.network.services

import ru.shafran.network.services.data.*

interface ServicesRepository {

    suspend fun getAllServices(data: GetAllServicesRequest): GetAllServicesResponse

    suspend fun createService(data: CreateServiceRequest): CreateServiceResponse

    suspend fun getServiceById(data: GetServiceByIdRequest): GetServiceByIdResponse

    suspend fun addConfiguration(data: CreateConfigurationRequest): CreateConfigurationResponse
    suspend fun deactivateConfiguration(data: DeactivateServiceConfigurationRequest): DeactivateServiceConfigurationResponse
}