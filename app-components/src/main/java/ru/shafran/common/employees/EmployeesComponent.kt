package ru.shafran.common.employees

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.employees.list.EmployeesListHostComponent

class EmployeesComponent(componentContext: ComponentContext) : Employees,
    ComponentContext by componentContext {
    val router = router(initialConfiguration = Employees.Configuration.EmployeeList,
        childFactory = this::createChild)

    private fun createChild(
        configuration: Employees.Configuration,
        componentContext: ComponentContext,
    ): Employees.Child {
        return when (configuration) {
            Employees.Configuration.EmployeeList ->
                Employees.Child.EmployeeList(EmployeesListHostComponent(componentContext))
        }
    }

    override val routerState: Value<RouterState<Employees.Configuration, Employees.Child>>
        get() = router.state



}

