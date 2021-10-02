package ru.shafran.cards.ui.component.employees.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.map
import ru.shafran.cards.data.employee.toModel
import ru.shafran.network.employee.EmployeesListStore

class EmployeesListComponent(
    componentContext: ComponentContext,
    private val employeesListStore: EmployeesListStore,
    private val onCreateEmployee: () -> Unit,
    private val onSelected: (Long) -> Unit,
) : EmployeesList, ComponentContext by componentContext {

    override val employees =
        employeesListStore.states.map { state ->
            when (state) {
                is EmployeesListStore.State.EmployeesLoaded -> {
                    state.employees.map { it.toModel() }
                }
                else -> {
                    null
                }
            }
        }

    init {
        onUpdate()
    }

    override fun onSelected(employeeId: Long) {
        onSelected.invoke(employeeId)
    }

    override fun onCreateEmployee() {
        onCreateEmployee.invoke()
    }

    override fun onUpdate() {
        employeesListStore.accept(EmployeesListStore.Intent.LoadEmployees)
    }

}
