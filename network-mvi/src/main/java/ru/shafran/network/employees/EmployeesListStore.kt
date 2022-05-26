package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.employees.data.Employee

interface EmployeesListStore :
    Store<EmployeesListStore.Intent, EmployeesListStore.State, EmployeesListStore.Label> {

    sealed class Intent {
        data class LoadEmployees(
            val companyId: CompanyId,
            val offset: Int = 30,
            val page: Int = 0,
        ) : Intent()
    }

    sealed class State {

        object Empty : State()

        data class EmployeesLoaded(
            val employees: List<Employee>,
        ) : State()

        data class Loading(
            val previousEmployees: List<Employee>? = null,
        ) : State()

        sealed class Error : State() {
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }

    }

    sealed class Label {}

}