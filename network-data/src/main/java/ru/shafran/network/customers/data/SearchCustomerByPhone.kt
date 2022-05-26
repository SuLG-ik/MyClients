package ru.shafran.network.customers.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.PhoneNumber
import ru.shafran.network.companies.data.CompanyId

@Parcelize
@Serializable
data class SearchCustomerByPhoneRequest(
    val phoneNumber: PhoneNumber,
    val companyId: CompanyId,
) : Parcelable

@Parcelize
@Serializable
data class SearchCustomerByPhoneResponse(
    val searchResult: List<FoundCustomerItem>,
) : Parcelable
