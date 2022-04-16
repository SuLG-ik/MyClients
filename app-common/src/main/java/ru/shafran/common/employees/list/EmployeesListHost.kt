package ru.shafran.common.employees.list

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.employees.details.host.EmployeeDetailsHost
import ru.shafran.common.utils.Updatable
import ru.shafran.network.employees.data.Employee

interface EmployeesListHost : Updatable {

    val employeeDetails: EmployeeDetailsHost

    override val onUpdate: (() -> Unit)

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        data class EmployeesList(val employees: List<Employee>) :
            Configuration()

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        object UnknownError : Configuration()

    }


    sealed class Child {

        class EmployeesList(
            val component: ru.shafran.common.employees.list.EmployeesList,
        ) : Child()

        class Loading(
            val component: ru.shafran.common.loading.Loading,
        ) : Child()

        class Error(
            val component: ru.shafran.common.error.Error,
        ) : Child()
    }


}