package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.employees.data.Employee

interface EmployeesListStore :
    Store<EmployeesListStore.Intent, EmployeesListStore.State, EmployeesListStore.Label> {

    sealed class Intent {
        data class LoadEmployees(val page: Int = 0): Intent()
    }

    sealed class State {
        data class EmployeesLoaded(
            val employees: List<Employee>,
        ) : State()

        data class Loading(
            val previousEmployees: List<Employee>,
        ) : State()
        sealed class Error : State() {
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }
    }

    sealed class Label {}

}