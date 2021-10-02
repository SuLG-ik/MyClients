package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.employee.Employee

interface EmployeesListStore : Store<EmployeesListStore.Intent, EmployeesListStore.State, Nothing> {

    sealed class Intent {
        object LoadEmployees : Intent()
    }

    sealed class State {
        data class EmployeesLoaded(
            val employees: List<Employee> = emptyList(),
        ) : State()

        object Loading : State()
        sealed class Error : State() {
            object InternalServerError : Error()
            object ConnectionLost: Error()
            object UnknownError : Error()
        }
    }


}