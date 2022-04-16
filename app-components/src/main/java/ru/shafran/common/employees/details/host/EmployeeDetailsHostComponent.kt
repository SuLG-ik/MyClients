package ru.shafran.common.employees.details.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.common.employees.details.create.EmployeeCreatingHostComponent
import ru.shafran.common.employees.details.info.EmployeeInfoHostComponent
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.employees.data.Employee

class EmployeeDetailsHostComponent(
    componentContext: ComponentContext,
    private val onUpdateEmployeesList: () -> Unit,
) : EmployeeDetailsHost, ComponentContext by componentContext {

    private val onBack: () -> Unit = {
        if (router.state.value.backStack.size > 1)
            router.pop()
        else
            onHide()
    }

    override val isShown: Value<Boolean>
        get() = router.state.map { it.activeChild.configuration !is EmployeeDetailsHost.Configuration.Hidden }

    override val onHide: () -> Unit = {
        router.replaceAll(EmployeeDetailsHost.Configuration.Hidden)
    }
    override val onShowEmployee: (Employee) -> Unit = {
        router.replaceAll(EmployeeDetailsHost.Configuration.EmployeeInfo(it))
    }
    override val onCreateEmployee: () -> Unit = {
        router.replaceAll(EmployeeDetailsHost.Configuration.EmployeeCreating())
    }
    private val router = router<EmployeeDetailsHost.Configuration, EmployeeDetailsHost.Child>(
        initialConfiguration = EmployeeDetailsHost.Configuration.Hidden,
        childFactory = this::createChild
    )

    private fun createChild(
        configuration: EmployeeDetailsHost.Configuration,
        componentContext: ComponentContext,
    ): EmployeeDetailsHost.Child {
        return when (configuration) {
            is EmployeeDetailsHost.Configuration.EmployeeInfo ->
                EmployeeDetailsHost.Child.EmployeeInfo(
                    EmployeeInfoHostComponent(
                        componentContext = componentContext,
                        employee = configuration.employee,
                        onBack = onBack,
                    )
                )
            is EmployeeDetailsHost.Configuration.Hidden ->
                EmployeeDetailsHost.Child.Hidden
            is EmployeeDetailsHost.Configuration.EmployeeCreating ->
                EmployeeDetailsHost.Child.EmployeeCreating(
                    EmployeeCreatingHostComponent(
                        componentContext = componentContext,
                        onEmployeeCreated = {
                            onUpdateEmployeesList()
                            onShowEmployee(it)
                        }
                    )
                )
        }
    }

    override val routerState: Value<RouterState<EmployeeDetailsHost.Configuration, EmployeeDetailsHost.Child>>
        get() = router.state

}