package ru.shafran.cards.ui.component.employees.info

import ru.shafran.cards.data.employee.EmployeeModel

interface EmployeeInfo {

    val employee: EmployeeModel

    fun onDelete()

    fun onEdit()

}