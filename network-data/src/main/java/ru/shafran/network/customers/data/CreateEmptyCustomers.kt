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