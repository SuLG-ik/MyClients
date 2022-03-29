package ru.shafran.network.customers.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.session.data.Session


@Parcelize
@Serializable
data class FoundCustomerItem(
    val customer: Customer.ActivatedCustomer,
    val lastUsedSession: Session?,
) : Parcelable