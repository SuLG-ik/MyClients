package ru.shafran.network.employees.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GetAllEmployeesRequest(
    val offset: Int = 30,
    val page: Int = 0,
) : Parcelable

@Parcelize
@Serializable
data class GetAllEmployeesResponse(
    val employees: List<Employee>,
    val offset: Int,
    val page: Int,
) : Parcelable