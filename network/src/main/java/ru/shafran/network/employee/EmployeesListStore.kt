package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.employee.Employee

interface EmployeesListStore : Store<EmployeesListStore.Intent, EmployeesListStore.State, Nothing> {

    sealed class Intent {
        object LoadEmployees : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val employees: List<Employee> = emptyList(),
    )

    sealed class Label {
        data class ShowError(val message: String)
    }


}