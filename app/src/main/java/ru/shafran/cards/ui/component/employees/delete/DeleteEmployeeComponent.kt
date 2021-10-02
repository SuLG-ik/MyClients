package ru.shafran.cards.ui.component.employees.delete

import ru.shafran.cards.data.employee.EmployeeModel

class DeleteEmployeeComponent(
    override val employee: EmployeeModel,
    private val onAgree: () -> Unit,
    private val onCancel: () -> Unit,
) : DeleteEmployee {

    override fun onAgree() {
        onAgree.invoke()
    }

    override fun onCancel() {
        onCancel.invoke()
    }
}