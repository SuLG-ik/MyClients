package ru.shafran.common.employees.picker.employee

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.employees.data.Employee

interface EmployeeSelector {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Loading : Configuration()

        @Parcelize
        class EmployeeList(
            val employees: List<Employee>,
            val selectedEmployee: Employee? = null,
        ) : Configuration()

    }

    sealed class Child {

        class Loading(val component: ru.shafran.common.loading.Loading) : Child()

        class EmployeeList(val component: EmployeesListSelector) : Child()


    }

}