package ru.shafran.network.customers

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.shafran.network.customers.data.CreateCustomerRequest
import ru.shafran.network.customers.data.CreateCustomerResponse
import ru.shafran.network.customers.data.CreateEmptyCustomersRequest
import ru.shafran.network.customers.data.CreateEmptyCustomersResponse
import ru.shafran.network.customers.data.EditCustomerDataResponse
import ru.shafran.network.customers.data.EditCustomerRequest
import ru.shafran.network.customers.data.GetAllCustomersRequest
import ru.shafran.network.customers.data.GetAllCustomersResponse
import ru.shafran.network.customers.data.GetCustomerByIdRequest
import ru.shafran.network.customers.data.GetCustomerByIdResponse
import ru.shafran.network.customers.data.GetCustomerByTokenRequest
import ru.shafran.network.customers.data.GetCustomerByTokenResponse
import ru.shafran.network.customers.data.SearchCustomerByPhoneRequest
import ru.shafran.network.customers.data.SearchCustomerByPhoneResponse
import ru.shafran.network.tryRequest

internal class KtorCustomersRepository(
    private val httpClient: HttpClient,
) : CustomersRepository {

    override suspend fun searchCustomerByPhone(data: SearchCustomerByPhoneRequest): SearchCustomerByPhoneResponse {
        return tryRequest {
            httpClient.get("customers/searchCustomerByPhone") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun createCustomer(data: CreateCustomerRequest): CreateCustomerResponse {
        return tryRequest {
            httpClient.post("customers/createCustomer") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun getCustomerById(data: GetCustomerByIdRequest): GetCustomerByIdResponse {
        return tryRequest {
            httpClient.get("customers/getCustomerById") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun getCustomerByToken(data: GetCustomerByTokenRequest): GetCustomerByTokenResponse {
        return tryRequest {
            httpClient.get("customers/getCustomerByToken") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun getAllCustomers(data: GetAllCustomersRequest): GetAllCustomersResponse {
        return tryRequest {
            httpClient.get("customers/getAllCustomers") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun createEmptyCustomers(data: CreateEmptyCustomersRequest): CreateEmptyCustomersResponse {
        return tryRequest {
            httpClient.post("customers/createEmptyCustomers") {
                setBody(data)
            }.body()
        }
    }

    override suspend fun editCustomerData(data: EditCustomerRequest): EditCustomerDataResponse {
        return tryRequest {
            httpClient.patch("customers/editCustomerData") {
                setBody(data)
            }.body()
        }
    }
}