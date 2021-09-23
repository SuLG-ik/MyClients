package ru.shafran.cards.ui.component.employees

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.employee.EmployeeModel
import ru.shafran.cards.data.employee.EmployeeDataModel

interface Employees {

    fun onCreateEmployee()

    fun onShowAllEmployees()

    val routerState : Value<RouterState<EmployeesConfiguration, Child>>


    interface EmployeesViewModel : InstanceKeeper.Instance {

        fun updateEmployees()

        fun loadEmployee(id: Long)

        fun createEmployee(data: EmployeeDataModel)

        val employees: StateFlow<List<EmployeeModel>?>

    }

    sealed class Child {

        class EmployeesList(val employeesList: ru.shafran.cards.ui.component.employeeslist.EmployeesList): Child()

        class CreateEmployee(val createEmployee: ru.shafran.cards.ui.component.createemployee.CreateEmployee): Child()

    }
}