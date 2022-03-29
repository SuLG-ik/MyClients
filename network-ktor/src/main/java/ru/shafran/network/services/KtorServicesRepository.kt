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
import ru.shafran.network.services.data.GetAllServicesRequest
import ru.shafran.network.services.data.GetAllServicesResponse
import ru.shafran.network.services.data.GetServiceByIdRequest
import ru.shafran.network.services.data.GetServiceByIdResponse

internal class KtorServicesRepository(
    private val httpClient: HttpClient,
) : ServicesRepository {

    override suspend fun getAllServices(data: GetAllServicesRequest): GetAllServicesResponse {
        return httpClient.get("services/getAllServices",) {
            setBody(data)
        }.body()
    }

    override suspend fun createService(data: CreateServiceRequest): CreateServiceResponse {
        return httpClient.post("services/createService",) {
            setBody(data)
        }.body()
    }

    override suspend fun getServiceById(data: GetServiceByIdRequest): GetServiceByIdResponse {
        return httpClient.get("services/getServiceById",) {
            setBody(data)
        }.body()
    }

    override suspend fun addConfiguration(data: CreateConfigurationRequest): CreateConfigurationResponse {
        return httpClient.put("services/addConfiguration",) {
            setBody(data)
        }.body()
    }

    override suspend fun deactivateConfiguration(data: DeactivateServiceConfigurationRequest): DeactivateServiceConfigurationResponse {
        return httpClient.delete("services/deactivateConfiguration",) {
            setBody(data)
        }.body()
    }

}