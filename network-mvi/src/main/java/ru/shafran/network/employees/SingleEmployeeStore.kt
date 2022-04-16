package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.employees.data.GetEmployeeWithIdRequest

interface SingleEmployeeStore :
    Store<SingleEmployeeStore.Intent, SingleEmployeeStore.State, SingleEmployeeStore.Label> {

    sealed class Intent {
        data class LoadEmployee(val employee: Employee) : Intent()
        data class LoadEmployeeWithId(val request: GetEmployeeWithIdRequest) : Intent()
    }

    sealed class State {
        object Empty : State()

        data class EmployeeLoaded(val employee: Employee) : State()

        class Loading : State()

        sealed class Error : State() {
            data class EmployeeDoesNotExist(val name: String) : Error()
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }
    }

    sealed class Label {}

}