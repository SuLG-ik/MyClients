package ru.shafran.cards.ui.component.employeesdetails

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.cards.data.employee.EmployeeModel

sealed class EmployeesDetailsConfiguration: Parcelable {

    @Parcelize
    object CreateEmployee: EmployeesDetailsConfiguration()

    @Parcelize
    class EmployeeInfo(val data: EmployeeModel): EmployeesDetailsConfiguration()

    @Parcelize
    class Loading(val message: String): EmployeesDetailsConfiguration()

    @Parcelize
    object Hidden: EmployeesDetailsConfiguration()

}