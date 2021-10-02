package ru.shafran.cards.ui.component.employees.details

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.cards.data.employee.EmployeeModel

sealed class EmployeesDetailsConfiguration: Parcelable {

    @Parcelize
    object CreateEmployee: EmployeesDetailsConfiguration()

    @Parcelize
    class EmployeeInfo(val employee: EmployeeModel): EmployeesDetailsConfiguration()

    @Parcelize
    class DeleteEmployee(val employee: EmployeeModel): EmployeesDetailsConfiguration()

    @Parcelize
    class EditEmployee(val employee: EmployeeModel): EmployeesDetailsConfiguration()

    @Parcelize
    class Loading(val message: String): EmployeesDetailsConfiguration()

    @Parcelize
    class Error(val message: String): EmployeesDetailsConfiguration()

    @Parcelize
    object Hidden: EmployeesDetailsConfiguration()

}