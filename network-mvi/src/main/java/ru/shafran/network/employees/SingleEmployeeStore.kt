package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.employees.data.GetEmployeeByIdRequest
import ru.shafran.network.employees.data.LayoffEmployeeRequest

interface SingleEmployeeStore :
    Store<SingleEmployeeStore.Intent, SingleEmployeeStore.State, SingleEmployeeStore.Label> {

    sealed class Intent {
        data class LoadEmployee(val id: GetEmployeeByIdRequest) : Intent()
        data class CreateEmployee(val data: CreateEmployeeRequest): Intent()
        data class EditEmployee(val data: Int = TODO()): Intent()
        data class LayoffEmployee(val layoff: LayoffEmployeeRequest): Intent()
    }

    sealed class State {
        data class EmployeeLoaded(val employee: Employee) : State()
        data class Loading(
            val loadedEmployees: List<Employee>,
        ): State()

        object Hidden: State()

        sealed class Error : State() {
            data class EmployeeDoesNotExist(val name: String): Error()
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }
    }

    sealed class Label {}

}