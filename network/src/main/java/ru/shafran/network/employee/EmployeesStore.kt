package ru.shafran.network.employee

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.data.employee.Employee
import ru.shafran.network.data.employee.EmployeeData

interface EmployeesStore : Store<EmployeesStore.Intent, EmployeesStore.State, Nothing> {

    sealed class Intent {
        object LoadEmployees : Intent()
        data class CreateEmployee(
            val employeeData: EmployeeData,
        ) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val employees: List<Employee> = emptyList(),
    )

    sealed class Label {
        data class ShowError(val message: String)
    }


}