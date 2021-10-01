package ru.shafran.cards.ui.component.employees

import ru.shafran.cards.ui.component.employeesdetails.EmployeesDetails
import ru.shafran.cards.ui.component.employeeslist.EmployeesList

interface Employees {

    val employeesList: EmployeesList

    val employeesDetails: EmployeesDetails

    fun onCreateEmployee()
}