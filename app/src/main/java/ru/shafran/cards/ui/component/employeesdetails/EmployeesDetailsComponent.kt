package ru.shafran.cards.ui.component.employeesdetails

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
import ru.shafran.cards.ui.component.createemployee.CreateEmployeeComponent
import ru.shafran.cards.ui.component.employeeinfo.EmployeeInfoComponent
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
                is SingleEmployeeStore.State.Loaded -> {
                    onShowInfo(state.employee.toModel())
                }
                is SingleEmployeeStore.State.Loading -> {
                    onLoading()
                }
            }
        }.launchIn(scope)
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

    private fun createChild(
        configuration: EmployeesDetailsConfiguration,
        componentContext: ComponentContext,
    ): EmployeesDetails.Child {
        return when (configuration) {
            EmployeesDetailsConfiguration.CreateEmployee -> EmployeesDetails.Child.CreateEmployee(
                CreateEmployeeComponent(onCreateEmployee = this::onCreateEmployee)
            )
            is EmployeesDetailsConfiguration.EmployeeInfo -> EmployeesDetails.Child.EmployeeInfo(
                EmployeeInfoComponent(configuration.data)
            )
            EmployeesDetailsConfiguration.Hidden -> EmployeesDetails.Child.Hidden
            is EmployeesDetailsConfiguration.Loading -> EmployeesDetails.Child.Loading(
                LoadingComponent(configuration.message)
            )
        }
    }


    override val routerState: Value<RouterState<EmployeesDetailsConfiguration, EmployeesDetails.Child>>
        get() = router.state

    override val isShown: Value<Boolean> = routerState.map {
        it.activeChild.configuration !is EmployeesDetailsConfiguration.Hidden
    }
}
