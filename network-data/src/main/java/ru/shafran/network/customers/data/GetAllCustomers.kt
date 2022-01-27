package ru.shafran.network.customers.data

import kotlinx.serialization.Serializable

@Serializable
data class GetAllCustomersRequest(
    val page: Int? = null,
    val sort: String? = null,
)

@Serializable
data class GetAllCustomersResponse(
    val customers: List<Customer>,
)

@Serializable
data class GetCustomerByTokenRequest(
    val token: String,
)

@Serializable
data class GetCustomerByIdRequest(
    val id: String,
)

@Serializable
data class GetCustomerByIdResponse(
    val customer: Customer,
)


@Serializable
data class GetCustomerByTokenResponse(
    val customer: Customer,
)
