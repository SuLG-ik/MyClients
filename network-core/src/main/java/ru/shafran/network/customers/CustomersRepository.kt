package ru.shafran.network.customers

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

interface CustomersRepository {
    suspend fun getCustomerById(data: GetCustomerByIdRequest): GetCustomerByIdResponse
    suspend fun getCustomerByToken(data: GetCustomerByTokenRequest): GetCustomerByTokenResponse
    suspend fun getAllCustomers(data: GetAllCustomersRequest): GetAllCustomersResponse
    suspend fun createEmptyCustomers(data: CreateEmptyCustomersRequest): CreateEmptyCustomersResponse
    suspend fun editCustomerData(data: EditCustomerRequest): EditCustomerDataResponse
    suspend fun createCustomer(data: CreateCustomerRequest): CreateCustomerResponse
    suspend fun searchCustomerByPhone(data: SearchCustomerByPhoneRequest): SearchCustomerByPhoneResponse
}