package ru.shafran.cards.ui.component.employeeslist

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.map
import ru.shafran.cards.data.employee.toModel
import ru.shafran.network.employee.EmployeesListStore

class EmployeesListComponent(
    componentContext: ComponentContext,
    private val employeesListStore: EmployeesListStore,
    private val onCreateEmployee: () -> Unit,
) : EmployeesList, ComponentContext by componentContext {

    override val employees =
        employeesListStore.states.map { state ->
            Log.d("EmployeesDetailsCheck", state.toString())
            if (state.isLoading)
                null
            else
                state.employees.map { it.toModel() }
        }


    init {
        onUpdate()
    }


    override fun onCreateEmployee() {
        onCreateEmployee.invoke()
    }

    override fun onUpdate() {
        employeesListStore.accept(EmployeesListStore.Intent.LoadEmployees)
    }

}
