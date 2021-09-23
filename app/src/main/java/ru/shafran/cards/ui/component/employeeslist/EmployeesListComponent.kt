package ru.shafran.cards.ui.component.employeeslist

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.employee.EmployeeModel

class EmployeesListComponent(
    componentContext: ComponentContext,
    override val employees: Flow<List<EmployeeModel>>,
    override val isLoading: Flow<Boolean>,
    private val onCreateEmployee: () -> Unit,
    private val onUpdate: () -> Unit,
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
