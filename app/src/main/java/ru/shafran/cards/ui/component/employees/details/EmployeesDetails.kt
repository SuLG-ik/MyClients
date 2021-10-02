package ru.shafran.cards.ui.component.employees.details

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value

interface EmployeesDetails {

    val isShown: Value<Boolean>

    fun onCreateEmployee()

    fun onShowInfo(employeeId: Long)

    val routerState: Value<RouterState<EmployeesDetailsConfiguration, Child>>

    sealed class Child {

        class Loading(
            val component: ru.shafran.cards.ui.component.loading.Loading,
        ) : Child()

        class CreateEmployee(
            val component: ru.shafran.cards.ui.component.employees.create.CreateEmployee,
        ) : Child()

        class EmployeeInfo(
            val component: ru.shafran.cards.ui.component.employees.info.EmployeeInfo,
        ) : Child()

        class DeleteEmployee(
            val component: ru.shafran.cards.ui.component.employees.delete.DeleteEmployee,
        ) : Child()

        class EditEmployee(
            val component: ru.shafran.cards.ui.component.employees.edit.EditEmployee,
        ) : Child()

        object Hidden : Child()
    }

    fun onHide()
}