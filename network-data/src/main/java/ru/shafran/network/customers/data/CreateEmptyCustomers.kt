package ru.shafran.network.customers.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CreateEmptyCustomersRequest(
    val count: Int,
) : Parcelable

@Parcelize
@Serializable
data class CreateEmptyCustomersResponse(
    val customers: List<Customer.InactivatedCustomer>,
) : Parcelable

@Parcelize
@Serializable
data class CreateCustomerRequest(
    val data: EditableCustomerData,
) : Parcelable


@Parcelize
@Serializable
data class CreateCustomerResponse(
    val token: String,
    val customer: Customer.ActivatedCustomer,
) : Parcelable