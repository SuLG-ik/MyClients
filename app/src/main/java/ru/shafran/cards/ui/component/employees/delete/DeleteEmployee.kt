package ru.shafran.cards.ui.component.employees.delete

import ru.shafran.cards.data.employee.EmployeeModel

interface DeleteEmployee {

    val employee: EmployeeModel

    fun onAgree()

    fun onCancel()

}