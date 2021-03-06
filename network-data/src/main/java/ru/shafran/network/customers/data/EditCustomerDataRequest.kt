package ru.shafran.network.customers.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.Gender
import ru.shafran.network.PhoneNumber

@Serializable
@Parcelize
data class EditCustomerRequest(
    val customerId: String,
    val data: EditableCustomerData,
) : Parcelable

@Serializable
@Parcelize
data class EditableCustomerData(
    val name: String,
    val phone: PhoneNumber?,
    val remark: String,
    val gender: Gender,
) : Parcelable

@Serializable
data class EditCustomerDataResponse(
    val customer: Customer.ActivatedCustomer,
)
