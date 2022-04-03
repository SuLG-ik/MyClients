package ru.shafran.common.employees

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.common.employees.list.EmployeesListHost

interface Employees {

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object EmployeeList : Configuration()

    }

    sealed class Child {
        class EmployeeList(
            val component: EmployeesListHost,
        ) : Child()
    }

}