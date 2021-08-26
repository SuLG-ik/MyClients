package ru.shafran.cards.ui.component.employeeslist

import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.employee.Employee

interface EmployeesList {

    fun onCreateEmployee()

    fun onUpdate()

    val employees: StateFlow<List<Employee>?>

}