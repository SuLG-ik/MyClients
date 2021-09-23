package ru.shafran.cards.ui.component.employees

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.map
import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.data.employee.toData
import ru.shafran.cards.data.employee.toModel
import ru.shafran.cards.ui.component.createemployee.CreateEmployeeComponent
import ru.shafran.cards.ui.component.employeeslist.EmployeesListComponent
import ru.shafran.cards.utils.get
import ru.shafran.cards.utils.stores
import ru.shafran.network.employee.EmployeesStore

class EmployeesComponent(componentContext: ComponentContext) : Employees,
    ComponentContext by componentContext {

    private val employeesStore by instanceKeeper.stores { EmployeesStore(get(), get()) }

    private val employees = employeesStore.states.map { it.employees.map { employee ->  employee.toModel() } }


    private val isLoading = employeesStore.states.map { it.isLoading }

    override fun onShowAllEmployees() {
        router.push(EmployeesConfiguration.EmployeesList)
    }

    override fun onCreateEmployee() {
        router.push(EmployeesConfiguration.CreateEmployee)
    }

    private val router =
        router<EmployeesConfiguration, Employees.Child>(
            EmployeesConfiguration.EmployeesList,
            childFactory = this::createChild
        )


    private fun createChild(
        configuration: EmployeesConfiguration,
        componentContext: ComponentContext,
    ): Employees.Child {
        return when (configuration) {
            is EmployeesConfiguration.EmployeesList -> {
                Employees.Child.EmployeesList(
                    EmployeesListComponent(
                        componentContext,
                        employees = employees,
                        isLoading = isLoading,
                        onUpdate = this::onUpdateEmployees,
                        onCreateEmployee = this::onCreateEmployee,
                    )
                )
            }
            is EmployeesConfiguration.CreateEmployee -> {
                Employees.Child.CreateEmployee(
                    CreateEmployeeComponent(
                        onCreateEmployee = {
                            onCreateEmployee(it)
                            router.pop()
                        }
                    )
                )
            }
        }
    }

    private fun onCreateEmployee(data: EmployeeDataModel) {
        employeesStore.accept(EmployeesStore.Intent.CreateEmployee(data.toData()))
    }

    private fun onUpdateEmployees() {
        employeesStore.accept(EmployeesStore.Intent.LoadEmployees)
    }

    override val routerState: Value<RouterState<EmployeesConfiguration, Employees.Child>> =
        router.state

}