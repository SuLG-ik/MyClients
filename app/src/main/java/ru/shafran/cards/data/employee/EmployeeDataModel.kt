package ru.shafran.cards.data.employee

import android.os.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.employee.EmployeeData

@Parcelize
data class EmployeeDataModel(
    val name: String
) : Parcelable

fun EmployeeData.toModel(): EmployeeDataModel {
    return EmployeeDataModel(
        name = name,
    )
}

fun EmployeeDataModel.toData(): EmployeeData {
    return EmployeeData(
        name = name,
    )
}