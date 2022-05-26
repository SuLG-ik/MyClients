package ru.shafran.network.session.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.services.data.ConfiguredService


@Serializable
@Parcelize
data class GetSessionUsagesHistoryRequest(
    val offset: Int = 30,
    val page: Int = 0,
    val companyId: CompanyId,
) : Parcelable


@Serializable
@Parcelize
data class GetSessionUsagesHistoryResponse(
    val usages: List<SessionUsageHistoryItem>,
    val offset: Int,
    val page: Int,
) : Parcelable

@Parcelize
@Serializable
data class SessionUsageHistoryItem(
    val service: ConfiguredService,
    val usage: SessionUsage,
    val customer: Customer.ActivatedCustomer,
) : Parcelable