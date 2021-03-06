@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.network.employees.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.network.Gender
import ru.shafran.network.assets.data.AssetData
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime


@Parcelize
@Serializable
data class Employee(
    val id: String,
    val data: EmployeeData,
    val layoff: EmployeeLayoff?,
    val image: AssetData?,
) : Parcelable

@Parcelize
@Serializable
data class EmployeeData(
    val name: String,
    val description: String,
    val gender: Gender,
) : Parcelable

@Parcelize
@Serializable
data class EmployeeLayoff(
    val date: ZonedDateTime,
    val reason: String,
) : Parcelable