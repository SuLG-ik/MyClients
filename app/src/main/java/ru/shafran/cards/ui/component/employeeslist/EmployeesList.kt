package ru.shafran.cards.ui.component.employeeslist

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.employee.EmployeeModel

interface EmployeesList {

    fun onCreateEmployee()

    fun onUpdate()

    val employees: Flow<List<EmployeeModel>?>

}