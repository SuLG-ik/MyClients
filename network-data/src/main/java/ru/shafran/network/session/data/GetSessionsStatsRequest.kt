package ru.shafran.network.session.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.services.data.Service
import ru.shafran.network.utils.DatePeriod
import ru.shafran.network.utils.LocalDateSerializer
import java.time.LocalDate

@Serializable
@Parcelize
data class GetSessionsStatsRequest(
    val companyId: String,
    val period: DatePeriod = DatePeriod(from = LocalDate.now().minusMonths(1), to = LocalDate.now()),
): Parcelable

@Serializable
@Parcelize
data class GetSessionsStatsResponse(
    val stats: SessionStats,
    val period: DatePeriod,
): Parcelable

@Parcelize
@Serializable
data class SessionStats(
    val activatedSessionsCount: Int,
    val usedSessionsCount: Int,
    val popularService: MostPopularService,
    val usages: List<DayToCountUsageStat>,
): Parcelable

@Serializable
@Parcelize
data class DayToCountUsageStat(
    @Serializable(LocalDateSerializer::class)
    val day: LocalDate,
    val count: Int,
): Parcelable

@Parcelize
@Serializable
data class MostPopularService(
    val count: Int,
    val configuredService: Service,
): Parcelable