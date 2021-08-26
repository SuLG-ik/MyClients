package ru.shafran.cards.ui.component.createemployee

import ru.shafran.cards.data.employee.EmployeeData

class CreateEmployeeComponent(
    private val onCreateEmployee: (EmployeeData) -> Unit
): CreateEmployee {
    override fun onCreateEmployee(data: EmployeeData) {
        onCreateEmployee.invoke(data)
    }
}