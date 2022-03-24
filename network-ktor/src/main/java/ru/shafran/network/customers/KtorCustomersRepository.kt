package ru.shafran.network.customers

import beauty.shafran.network.customers.exceptions.CustomersException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
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

internal class KtorCustomersRepository(
    private val httpClient: HttpClient,
) : CustomersRepository {

    override suspend fun getCustomerById(data: GetCustomerByIdRequest): GetCustomerByIdResponse {
        return try {
            httpClient.get("/customers/getCustomerById") {
                setBody(data)
            }.body()
        } catch (e: ResponseException) {
            throw e.response.call.body<CustomersException>()
        }
    }

    override suspend fun getCustomerByToken(data: GetCustomerByTokenRequest): GetCustomerByTokenResponse {
        return try {
            httpClient.get("/customers/getCustomerByToken") {
                setBody(data)
            }.body()
        } catch (e: ResponseException) {
            throw e.response.call.body<CustomersException>()
        }
    }

    override suspend fun getAllCustomers(data: GetAllCustomersRequest): GetAllCustomersResponse {
        return try {
            httpClient.get("/customers/getAllCustomers") {
                setBody(data)
            }.body()
        } catch (e: ResponseException) {
            throw e.response.call.body<CustomersException>()
        }
    }

    override suspend fun createEmptyCustomers(data: CreateEmptyCustomersRequest): CreateEmptyCustomersResponse {
        return try {
            httpClient.post("/customers/createEmptyCustomers") {
                setBody(data)
            }.body()
        } catch (e: ResponseException) {
            throw e.response.call.body<CustomersException>()
        }
    }

    override suspend fun editCustomerData(data: EditCustomerRequest): EditCustomerDataResponse {
        return try {
            httpClient.patch("/customers/editCustomerData") {
                setBody(data)
            }.body()
        } catch (e: ResponseException) {
            throw e.response.call.body<CustomersException>()
        }
    }
}