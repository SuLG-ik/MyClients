package ru.shafran.network.services.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.companies.data.CompanyId

@Parcelize
@Serializable
data class GetAllServicesRequest(
    val offset: Int = 30,
    val page: Int = 0,
    val companyId: CompanyId,
) : Parcelable

@Parcelize
@Serializable
data class GetAllServicesResponse(
    val services: List<Service>,
    val offset: Int = 30,
    val page: Int = 0,
) : Parcelable