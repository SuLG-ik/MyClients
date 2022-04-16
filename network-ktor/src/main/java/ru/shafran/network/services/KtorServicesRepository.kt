package ru.shafran.network.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
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

internal class KtorServicesRepository(
    private val httpClient: HttpClient,
) : ServicesRepository {


    override suspend fun editService(request: EditServiceRequest): EditServiceResponse {
        return httpClient.patch("services/editService",) {
            setBody(request)
        }.body()
    }

    override suspend fun getAllServices(request: GetAllServicesRequest): GetAllServicesResponse {
        return httpClient.get("services/getAllServices",) {
            setBody(request)
        }.body()
    }

    override suspend fun createService(request: CreateServiceRequest): CreateServiceResponse {
        return httpClient.post("services/createService",) {
            setBody(request)
        }.body()
    }

    override suspend fun getServiceById(request: GetServiceByIdRequest): GetServiceByIdResponse {
        return httpClient.get("services/getServiceById",) {
            setBody(request)
        }.body()
    }

    override suspend fun createConfiguration(request: CreateConfigurationRequest): CreateConfigurationResponse {
        return httpClient.put("services/createConfiguration",) {
            setBody(request)
        }.body()
    }

    override suspend fun deactivateConfiguration(request: DeactivateServiceConfigurationRequest): DeactivateServiceConfigurationResponse {
        return httpClient.delete("services/deactivateConfiguration",) {
            setBody(request)
        }.body()
    }

}