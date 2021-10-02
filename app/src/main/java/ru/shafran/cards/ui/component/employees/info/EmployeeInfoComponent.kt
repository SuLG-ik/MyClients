package ru.shafran.cards.ui.component.employees.info

import ru.shafran.cards.data.employee.EmployeeModel

class EmployeeInfoComponent(
    override val employee: EmployeeModel,
    private val onDelete: () -> Unit,
    private val onEdit: () -> Unit,
) : EmployeeInfo {

    override fun onDelete() {
        onDelete.invoke()
    }

    override fun onEdit() {
        onEdit.invoke()
    }

}