package ru.shafran.network.employees.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.Gender

@Parcelize
@Serializable
data class CreateEmployeeRequest(
    val data: CreateEmployeeRequestData,
    val companyId: String
) : Parcelable

@Parcelize
@Serializable
data class CreateEmployeeRequestData(
    val name: String,
    val gender: Gender,
    val description: String,
): Parcelable

@Parcelize
@Serializable
data class CreateEmployeeResponse(
    val employee: Employee,
) : Parcelable