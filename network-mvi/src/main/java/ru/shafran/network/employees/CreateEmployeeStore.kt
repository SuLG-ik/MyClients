package ru.shafran.network.employees

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.CreateEmployeeRequestData
import ru.shafran.network.employees.data.Employee

interface CreateEmployeeStore :
    Store<CreateEmployeeStore.Intent, CreateEmployeeStore.State, CreateEmployeeStore.Label> {

    sealed class Intent {

        data class LoadDetails(val request: CreateEmployeeRequestData? = null) : Intent()

        data class CreateEmployee(
            val data: CreateEmployeeRequestData,
            val companyId: CompanyId,
        ) : Intent()

    }

    sealed class State {

        object Empty : State()

        data class CreateEmployee(
            val request: CreateEmployeeRequestData? = null,
        ) : State()

        data class CreateEmployeeLoading(val request: CreateEmployeeRequest) : State()

        sealed class Error : State() {
            abstract val request: CreateEmployeeRequest

            data class Unknown(override val request: CreateEmployeeRequest) : Error()
        }

    }

    sealed class Label {

        data class OnEmployeeCreated(val employee: Employee) : Label()

    }


}