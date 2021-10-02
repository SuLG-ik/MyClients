package ru.shafran.cards.ui.component.employees.edit

import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.data.employee.EmployeeModel

interface EditEmployee {

    val employee: EmployeeModel

    fun onEditEmployee(data: EmployeeDataModel)

    fun onCancel()

}