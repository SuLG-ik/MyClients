package ru.shafran.cards.data.employee

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Employee(
    val id: Long,
    val data: EmployeeData
): Parcelable