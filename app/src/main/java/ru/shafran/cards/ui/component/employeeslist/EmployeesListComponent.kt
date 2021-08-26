package ru.shafran.cards.ui.component.employeeslist

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.employee.Employee

class EmployeesListComponent(
    componentContext: ComponentContext,
    override val employees: StateFlow<List<Employee>?>,
    private val onUpdate: () -> Unit,
    private val onCreateEmployee: () -> Unit
) : EmployeesList, ComponentContext by componentContext {

    init {
        onUpdate()
    }

    override fun onCreateEmployee() {
        onCreateEmployee.invoke()
    }

    override fun onUpdate() {
        onUpdate.invoke()
    }

}
