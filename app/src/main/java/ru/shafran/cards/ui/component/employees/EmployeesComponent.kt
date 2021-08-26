package ru.shafran.cards.ui.component.employees

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.shafran.cards.data.employee.Employee
import ru.shafran.cards.data.employee.EmployeeData
import ru.shafran.cards.ui.component.createemployee.CreateEmployeeComponent
import ru.shafran.cards.ui.component.employeeslist.EmployeesListComponent
import ru.shafran.cards.usecase.UseCaseFactory
import ru.shafran.cards.utils.get
import ru.sulgik.common.asObservable
import ru.sulgik.common.execute

class EmployeesComponent(componentContext: ComponentContext) : Employees,
    ComponentContext by componentContext {

    private val viewModel =
        instanceKeeper.getOrCreate<Employees.EmployeesViewModel> { ViewModel(get()) }

    override fun onShowAllEmployees() {
        router.push(EmployeesConfiguration.EmployeesList)
    }

    override fun onCreateEmployee() {
        router.push(EmployeesConfiguration.CreateEmployee)
    }

    private val router =
        router<EmployeesConfiguration, Employees.Child>(EmployeesConfiguration.EmployeesList,
            childFactory = this::createChild)


    private fun createChild(
        configuration: EmployeesConfiguration,
        componentContext: ComponentContext,
    ): Employees.Child {
        return when (configuration) {
            is EmployeesConfiguration.EmployeesList -> {
                Employees.Child.EmployeesList(
                    EmployeesListComponent(
                        componentContext,
                        viewModel.employees,
                        viewModel::updateEmployees,
                        onCreateEmployee = {
                            onCreateEmployee()
                        }
                    )
                )
            }
            is EmployeesConfiguration.CreateEmployee -> {
                Employees.Child.CreateEmployee(
                    CreateEmployeeComponent(
                        onCreateEmployee = {
                            viewModel.createEmployee(it)
                            router.pop()
                        }
                    )
                )
            }
        }
    }

    override val routerState: Value<RouterState<EmployeesConfiguration, Employees.Child>> =
        router.state

    class ViewModel(
        useCasesFactory: UseCaseFactory,
    ) : Employees.EmployeesViewModel {

        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        private val getEmployeeByIdUseCase =
            useCasesFactory.getEmployeeById().asObservable(scope, Dispatchers.IO)

        private val getAllEmployeesUseCase =
            useCasesFactory.getAllEmployees().asObservable(scope, Dispatchers.IO)

        private val createEmployeeUseCase =
            useCasesFactory.createEmployee().asObservable(scope, Dispatchers.IO)

        override fun updateEmployees() {
            getAllEmployeesUseCase.execute()
        }

        override fun loadEmployee(id: Long) {
            getEmployeeByIdUseCase.execute(id)
        }

        override fun createEmployee(data: EmployeeData) {
            createEmployeeUseCase.execute(data)
        }

        override val employees: StateFlow<List<Employee>?> =
            getAllEmployeesUseCase.results.map { result ->
                result?.onSuccess {
                    return@map it
                }
                result?.onFailure {

                }
                return@map null
            }.stateIn(scope, SharingStarted.Lazily, null)

        override fun onDestroy() {
            scope.cancel()
        }


    }

}