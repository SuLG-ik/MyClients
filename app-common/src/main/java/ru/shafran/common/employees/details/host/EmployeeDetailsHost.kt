package ru.shafran.common.employees.details.host

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.employees.data.Employee

interface EmployeeDetailsHost {

    val isShown: Value<Boolean>

    val onHide: () -> Unit

    val onShowEmployee: (Employee) -> Unit
    val onCreateEmployee: () -> Unit

    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Configuration : Parcelable {

        @Parcelize
        object Hidden : Configuration()

        @Parcelize
        class EmployeeInfo(val employee: Employee) : Configuration()

        @Parcelize
        class EmployeeCreating : Configuration()

    }


    sealed class Child {
        object Hidden : Child()

        class EmployeeInfo(
            val component: ru.shafran.common.employees.details.info.EmployeeInfoHost,
        ) : Child()

        class EmployeeCreating(
            val component: ru.shafran.common.employees.details.create.EmployeeCreatingHost,
        ) : Child()

    }

}