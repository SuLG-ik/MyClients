package ru.shafran.common.employees.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.employees.SingleEmployeeStore
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.employees.data.GetEmployeeWithIdRequest
import ru.shafran.network.utils.reduceStates

class EmployeeInfoHostComponent(
    componentContext: ComponentContext,
    override val onBack: (() -> Unit)? = null,
    private val employeeId: String? = null,
    private val employee: Employee? = null,
) : EmployeeInfoHost, ComponentContext by componentContext {

    private val store = getStore<SingleEmployeeStore>()
        .reduceStates(this, this::reduceState)

    private fun reduceState(state: SingleEmployeeStore.State) {
        when (state) {
            is SingleEmployeeStore.State.EmployeeLoaded ->
                router.replaceAll(EmployeeInfoHost.Configuration.EmployeeLoaded(state.employee))
            is SingleEmployeeStore.State.Empty ->
                onUpdate()
            is SingleEmployeeStore.State.Error ->
                router.replaceAll(EmployeeInfoHost.Configuration.UnknownError)
            is SingleEmployeeStore.State.Loading ->
                router.replaceAll(EmployeeInfoHost.Configuration.Loading())
        }
    }

    private val onUpdate: () -> Unit = {
        when {
            employee != null -> {
                store.accept(SingleEmployeeStore.Intent.LoadEmployee(employee))
            }
            employeeId != null -> {
                store.accept(
                    SingleEmployeeStore.Intent.LoadEmployeeWithId(GetEmployeeWithIdRequest(
                        employeeId))
                )
            }
            else -> {
                throw IllegalArgumentException("EmployeeId or employee must not be null")
            }
        }
    }

    val router = router<EmployeeInfoHost.Configuration, EmployeeInfoHost.Child>(
        initialConfiguration = EmployeeInfoHost.Configuration.Loading(),
        childFactory = this::createChild,
    )


    private fun createChild(
        configuration: EmployeeInfoHost.Configuration,
        componentContext: ComponentContext,
    ): EmployeeInfoHost.Child {
        return when (configuration) {
            is EmployeeInfoHost.Configuration.EmployeeLoaded ->
                EmployeeInfoHost.Child.EmployeeLoaded(
                    EmployeeInfoComponent(
                        employee = configuration.employee,
                        onBack = onBack,
                    )
                )
            is EmployeeInfoHost.Configuration.Loading ->
                EmployeeInfoHost.Child.Loading(
                    LoadingComponent(
                        message = R.string.employees_loading,
                    )
                )
            is EmployeeInfoHost.Configuration.UnknownError ->
                EmployeeInfoHost.Child.Error(
                    ErrorComponent(
                        message = R.string.unknwon_error,
                        icon = R.drawable.error,
                        onContinue = onUpdate,
                    )
                )
        }

    }

    override val routerState: Value<RouterState<EmployeeInfoHost.Configuration, EmployeeInfoHost.Child>>
        get() = router.state
}