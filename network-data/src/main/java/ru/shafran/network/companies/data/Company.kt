package ru.shafran.network.companies.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
@Parcelize
data class Company(
    val id: CompanyId,
    val data: CompanyData,
) : Parcelable

@JvmInline
@Serializable
@Parcelize
value class CompanyId(val id: String) : Parcelable

@Serializable
@Parcelize
data class CompanyData(
    val title: String,
    val codeName: String,
    @Serializable(ZonedDateTimeSerializer::class)
    val creationDate: ZonedDateTime,
) : Parcelable