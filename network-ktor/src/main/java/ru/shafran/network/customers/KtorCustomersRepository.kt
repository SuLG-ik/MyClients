package ru.shafran.network.customers

import io.ktor.client.*
import io.ktor.client.request.*
import ru.shafran.network.customers.data.*

internal class KtorCustomersRepository(
    private val httpClient: HttpClient,
) : CustomersRepository {

    override suspend fun getCustomerById(data: GetCustomerByIdRequest): GetCustomerByIdResponse {
        return httpClient.get(
            path = "/customers/getCustomerById",
            body = data,
        )
    }

    override suspend fun getCustomerByToken(data: GetCustomerByTokenRequest): GetCustomerByTokenResponse {
        return httpClient.get(
            path = "/customers/getCustomerByToken",
            body = data,
        )
    }

    override suspend fun getAllCustomers(data: GetAllCustomersRequest): GetAllCustomersResponse {
        return httpClient.get(
            path = "/customers/getAllCustomers",
            body = data,
        )
    }

    override suspend fun createEmptyCustomers(data: CreateEmptyCustomersRequest): CreateEmptyCustomersResponse {
        return httpClient.post(
            path = "/customers/createEmptyCustomers",
            body = data,
        )
    }

    override suspend fun editCustomerData(data: EditCustomerRequest): EditCustomerDataResponse {
        return httpClient.patch(
            path = "/customers/editCustomerData",
            body = data,
        )
    }
}