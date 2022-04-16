package ru.shafran.network.services

import ru.shafran.network.services.data.CreateConfigurationRequest
import ru.shafran.network.services.data.CreateConfigurationResponse
import ru.shafran.network.services.data.CreateServiceRequest
import ru.shafran.network.services.data.CreateServiceResponse
import ru.shafran.network.services.data.DeactivateServiceConfigurationRequest
import ru.shafran.network.services.data.DeactivateServiceConfigurationResponse
import ru.shafran.network.services.data.EditServiceRequest
import ru.shafran.network.services.data.EditServiceResponse
import ru.shafran.network.services.data.GetAllServicesRequest
import ru.shafran.network.services.data.GetAllServicesResponse
import ru.shafran.network.services.data.GetServiceByIdRequest
import ru.shafran.network.services.data.GetServiceByIdResponse

interface ServicesRepository {

    suspend fun editService(request: EditServiceRequest): EditServiceResponse

    suspend fun getAllServices(request: GetAllServicesRequest): GetAllServicesResponse

    suspend fun createService(request: CreateServiceRequest): CreateServiceResponse

    suspend fun getServiceById(request: GetServiceByIdRequest): GetServiceByIdResponse

    suspend fun createConfiguration(request: CreateConfigurationRequest): CreateConfigurationResponse

    suspend fun deactivateConfiguration(request: DeactivateServiceConfigurationRequest): DeactivateServiceConfigurationResponse

}