package ru.shafran.cards.ui.component.employees

import ru.shafran.cards.ui.component.employees.details.EmployeesDetails
import ru.shafran.cards.ui.component.employees.list.EmployeesList

interface Employees {

    val employeesList: EmployeesList

    val employeesDetails: EmployeesDetails

    fun onCreateEmployee()
}