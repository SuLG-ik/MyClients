package ru.shafran.network.customers.data

import kotlinx.serialization.Serializable

@Serializable
data class EditCustomerRequest(
    val customerId: String,
    val data: CustomerData,
)

@Serializable
data class EditCustomerDataResponse(
    val customer: Customer.ActivatedCustomer,
)
