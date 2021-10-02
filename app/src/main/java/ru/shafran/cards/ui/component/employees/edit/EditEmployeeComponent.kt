package ru.shafran.cards.ui.component.employees.edit

import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.data.employee.EmployeeModel

class EditEmployeeComponent(
    override val employee: EmployeeModel,
    private val onEditEmployee: (EmployeeDataModel) -> Unit,
    private val onCancel: () -> Unit,
) : EditEmployee {
    override fun onEditEmployee(data: EmployeeDataModel) {
        onEditEmployee.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }
}