package ru.shafran.common.employees.picker

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.network.employees.data.Employee

interface EmployeePicker {

    val selectedEmployee: StateFlow<Employee?>

    val isPicking: MutableStateFlow<Boolean>

    val routerState: Value<RouterState<Configuration, Child>>

    fun onTogglePicking(isPicking: Boolean = !this.isPicking.value)

    sealed class Configuration : Parcelable {

        @Parcelize
        class EmployeeSelector(
            val selectedEmployee: Employee? = null,
        ) : Configuration()

    }

    sealed class Child {

        class EmployeeSelector(
            val component: ru.shafran.common.employees.picker.employee.EmployeeSelector,
        ) : Child()

    }

}