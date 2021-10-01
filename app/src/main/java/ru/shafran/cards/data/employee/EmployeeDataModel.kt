package ru.shafran.cards.data.employee

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.employee.EmployeeData

@Parcelize
data class EmployeeDataModel(
    val name: String,
    val image: ImageInfoModel?,
) : Parcelable

fun EmployeeData.toModel(): EmployeeDataModel {
    return EmployeeDataModel(
        name = name,
        image = image?.toModel(),
    )
}

fun EmployeeDataModel.toData(): EmployeeData {
    return EmployeeData(
        name = name,
        image = image?.toData(),
    )
}