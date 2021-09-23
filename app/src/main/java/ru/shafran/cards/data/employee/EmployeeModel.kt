package ru.shafran.cards.data.employee

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.employee.Employee

@Parcelize
data class EmployeeModel(
    val id: Long,
    val data: EmployeeDataModel
) : Parcelable

fun Employee.toModel(): EmployeeModel {
    return EmployeeModel(
        id = id,
        data = data.toModel(),
    )
}

fun EmployeeModel.toData(): Employee {
    return Employee(
        id = id,
        data = data.toData(),
    )
}