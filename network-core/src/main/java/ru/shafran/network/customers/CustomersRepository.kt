package ru.shafran.network.customers

import ru.shafran.network.customers.data.*

interface CustomersRepository {
    suspend fun getCustomerById(data: GetCustomerByIdRequest): GetCustomerByIdResponse
    suspend fun getCustomerByToken(data: GetCustomerByTokenRequest): GetCustomerByTokenResponse
    suspend fun getAllCustomers(data: GetAllCustomersRequest): GetAllCustomersResponse
    suspend fun createEmptyCustomers(data: CreateEmptyCustomersRequest): CreateEmptyCustomersResponse
    suspend fun editCustomerData(data: EditCustomerRequest): EditCustomerDataResponse
}