package ru.shafran.cards.ui.component.employeesdetails

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value

interface EmployeesDetails {

    val isShown: Value<Boolean>

    fun onCreateEmployee()

    fun onShowInfo(employeeId: Long)

    val routerState: Value<RouterState<EmployeesDetailsConfiguration, Child>>

    sealed class Child {

        class Loading(val component: ru.shafran.cards.ui.component.loading.Loading) : Child()

        class CreateEmployee(val component: ru.shafran.cards.ui.component.createemployee.CreateEmployee) :
            Child()

        class EmployeeInfo(val component: ru.shafran.cards.ui.component.employeeinfo.EmployeeInfo) :
            Child()

        object Hidden : Child()
    }

    fun onHide()
}