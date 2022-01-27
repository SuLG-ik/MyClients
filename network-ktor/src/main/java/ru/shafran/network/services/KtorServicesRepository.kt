package ru.shafran.network.services

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.services.data.*

internal class KtorServicesRepository(
    private val httpClient: HttpClient,
) : ServicesRepository {

    override suspend fun getAllServices(data: GetAllServicesRequest): GetAllServicesResponse {
        return httpClient.get(
            path = "/services/getAllServices",
            body = data,
        )
    }

    override suspend fun createService(data: CreateServiceRequest): CreateServiceResponse {
        return httpClient.post(
            path = "/services/createService",
            body = data,
        )
    }

    override suspend fun getServiceById(data: GetServiceByIdRequest): GetServiceByIdResponse {
        return httpClient.get(
            path = "/services/getServiceById",
            body = data,
        )
    }

    override suspend fun addConfiguration(data: CreateConfigurationRequest): CreateConfigurationResponse {
        return httpClient.put(
            path = "/services/addConfiguration",
            body = data,
        )
    }

    override suspend fun deactivateConfiguration(data: DeactivateServiceConfigurationRequest): DeactivateServiceConfigurationResponse {
        return httpClient.delete(
            path = "/services/deactivateConfiguration",
            body = data,
        )
    }

}