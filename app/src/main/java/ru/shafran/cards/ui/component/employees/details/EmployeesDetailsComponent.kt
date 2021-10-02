package ru.shafran.cards.ui.component.employees.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.replaceCurrent
import com.arkivanov.decompose.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.shafran.cards.data.employee.EmployeeDataModel
import ru.shafran.cards.data.employee.EmployeeModel
import ru.shafran.cards.data.employee.toData
import ru.shafran.cards.data.employee.toModel
import ru.shafran.cards.ui.component.employees.create.CreateEmployeeComponent
import ru.shafran.cards.ui.component.employees.delete.DeleteEmployeeComponent
import ru.shafran.cards.ui.component.employees.edit.EditEmployeeComponent
import ru.shafran.cards.ui.component.employees.info.EmployeeInfoComponent
import ru.shafran.cards.ui.component.loading.LoadingComponent
import ru.shafran.cards.utils.createCoroutineScope
import ru.shafran.network.employee.EmployeesListStore
import ru.shafran.network.employee.SingleEmployeeStore

class EmployeesDetailsComponent(
    componentContext: ComponentContext,
    private val employeesListStore: EmployeesListStore,
    private val singleEmployeeStore: SingleEmployeeStore,
) : EmployeesDetails, ComponentContext by componentContext {

    private val scope = createCoroutineScope()

    init {
        singleEmployeeStore.labels.onEach {
            employeesListStore.accept(EmployeesListStore.Intent.LoadEmployees)
        }.launchIn(scope)
        singleEmployeeStore.states.onEach { state ->
            when (state) {
                is SingleEmployeeStore.State.EmployeeLoaded -> {
                    onShowInfo(state.employee.toModel())
                }
                is SingleEmployeeStore.State.Loading -> {
                    onLoading()
                }
                SingleEmployeeStore.State.Error.ConnectionLost -> onUnknownError()
                SingleEmployeeStore.State.Error.InternalServerError -> onUnknownError()
                SingleEmployeeStore.State.Error.NotFoundException -> onUnknownError()
                SingleEmployeeStore.State.Error.UnknownError -> onUnknownError()
                SingleEmployeeStore.State.Hidden -> onHide()
            }
        }.launchIn(scope)
    }

    private fun onUnknownError() {
        router.replaceCurrent(EmployeesDetailsConfiguration.Error("Неизвестная ошибка"))
    }

    override fun onCreateEmployee() {
        router.replaceCurrent(EmployeesDetailsConfiguration.CreateEmployee)
    }

    private fun onCreateEmployee(data: EmployeeDataModel) {
        singleEmployeeStore.accept(SingleEmployeeStore.Intent.CreateEmployee(data.toData()))
    }

    override fun onShowInfo(employeeId: Long) {
        singleEmployeeStore.accept(SingleEmployeeStore.Intent.LoadEmployee(employeeId))
    }

    private fun onShowInfo(employee: EmployeeModel) {
        router.replaceCurrent(EmployeesDetailsConfiguration.EmployeeInfo(employee))
    }

    private fun onLoading() {
        router.replaceCurrent(EmployeesDetailsConfiguration.Loading("Загрузка..."))
    }

    override fun onHide() {
        router.replaceCurrent(EmployeesDetailsConfiguration.Hidden)
    }

    private val router =
        router<EmployeesDetailsConfiguration, EmployeesDetails.Child>(
            initialConfiguration = EmployeesDetailsConfiguration.Hidden,
            key = "employees_details",
            childFactory = this::createChild,
        )

    private fun onRequestEditEmployee(employee: EmployeeModel) {
        router.replaceCurrent(EmployeesDetailsConfiguration.EditEmployee(employee))
    }

    private fun onEditEmployee(employeeId: Long, data: EmployeeDataModel) {
        singleEmployeeStore.accept(
            SingleEmployeeStore.Intent.EditEmployee(employeeId, data.toData())
        )
    }

    private fun onRequestDeleteEmployee(employee: EmployeeModel) {
        router.replaceCurrent(
            EmployeesDetailsConfiguration.DeleteEmployee(employee)
        )
    }

    private fun onDeleteEmployee(employeeId: Long) {
        singleEmployeeStore.accept(
            SingleEmployeeStore.Intent.DeleteEmployee(employeeId)
        )
    }

    private fun createChild(
        configuration: EmployeesDetailsConfiguration,
        componentContext: ComponentContext,
    ): EmployeesDetails.Child {
        return when (configuration) {
            EmployeesDetailsConfiguration.CreateEmployee -> EmployeesDetails.Child.CreateEmployee(
                CreateEmployeeComponent(onCreateEmployee = this::onCreateEmployee)
            )
            is EmployeesDetailsConfiguration.EmployeeInfo -> EmployeesDetails.Child.EmployeeInfo(
                EmployeeInfoComponent(
                    configuration.employee,
                    onDelete = { onRequestDeleteEmployee(configuration.employee) },
                    onEdit = { onRequestEditEmployee(configuration.employee) }
                )
            )
            EmployeesDetailsConfiguration.Hidden -> EmployeesDetails.Child.Hidden
            is EmployeesDetailsConfiguration.Loading -> EmployeesDetails.Child.Loading(
                LoadingComponent(configuration.message)
            )
            is EmployeesDetailsConfiguration.DeleteEmployee -> EmployeesDetails.Child.DeleteEmployee(
                DeleteEmployeeComponent(
                    configuration.employee,
                    onAgree = {
                        onDeleteEmployee(configuration.employee.id)
                    },
                    onCancel = {
                        onShowInfo(configuration.employee)
                    })
            )
            is EmployeesDetailsConfiguration.EditEmployee -> {
                EmployeesDetails.Child.EditEmployee(
                   EditEmployeeComponent(configuration.employee, onEditEmployee = {
                       onEditEmployee(configuration.employee.id, it)
                   }, onCancel = {
                       onShowInfo(configuration.employee)
                   })
                )
            }
            is EmployeesDetailsConfiguration.Error -> TODO()
        }
    }


    override val routerState: Value<RouterState<EmployeesDetailsConfiguration, EmployeesDetails.Child>>
        get() = router.state

    override val isShown: Value<Boolean> = routerState.map {
        it.activeChild.configuration !is EmployeesDetailsConfiguration.Hidden
    }
}
