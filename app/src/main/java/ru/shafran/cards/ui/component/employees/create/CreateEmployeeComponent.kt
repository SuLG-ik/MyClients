package ru.shafran.cards.ui.component.employees.create

import ru.shafran.cards.data.employee.EmployeeDataModel

class CreateEmployeeComponent(
    private val onCreateEmployee: (EmployeeDataModel) -> Unit
): CreateEmployee {
    override fun onCreateEmployee(data: EmployeeDataModel) {
        onCreateEmployee.invoke(data)
    }
}