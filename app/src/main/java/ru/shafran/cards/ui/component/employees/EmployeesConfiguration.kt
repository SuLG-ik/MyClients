package ru.shafran.cards.ui.component.employees

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class EmployeesConfiguration : Parcelable{

    @Parcelize
    object EmployeesList : EmployeesConfiguration()

    @Parcelize
    object CreateEmployee: EmployeesConfiguration()

}