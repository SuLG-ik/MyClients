package ru.shafran.common.employees.picker.employee

import ru.shafran.network.employees.data.Employee

interface EmployeesListSelector {

    val selectedEmployee: Employee?

    val employees: List<Employee>

    fun onSelect(employee: Employee)

}