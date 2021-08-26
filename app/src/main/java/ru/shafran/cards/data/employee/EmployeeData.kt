package ru.shafran.cards.data.employee

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class EmployeeData(
    val name: String
): Parcelable