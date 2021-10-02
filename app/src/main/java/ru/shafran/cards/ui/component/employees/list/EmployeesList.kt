package ru.shafran.cards.ui.component.employees.list

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.employee.EmployeeModel

interface EmployeesList {

    fun onCreateEmployee()

    fun onUpdate()

    fun onSelected(employeeId: Long)

    val employees: Flow<List<EmployeeModel>?>

}