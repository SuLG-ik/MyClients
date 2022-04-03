package ru.shafran.common.employees.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.employees.EmployeesListStore
import ru.shafran.network.utils.reduceStates

class EmployeesListHostComponent(componentContext: ComponentContext) : EmployeesListHost,
    ComponentContext by componentContext {

    private val store = getStore<EmployeesListStore>().reduceStates(this, this::reduceState)

    private fun reduceState(state: EmployeesListStore.State) {
        when (state) {
            is EmployeesListStore.State.EmployeesLoaded ->
                router.replaceAll(EmployeesListHost.Configuration.EmployeesList(state.employees))
            is EmployeesListStore.State.Error ->
                router.replaceAll(EmployeesListHost.Configuration.UnknownError)
            is EmployeesListStore.State.Loading ->
                router.replaceAll(EmployeesListHost.Configuration.Loading)
            is EmployeesListStore.State.Empty -> onUpdate()
        }
    }

    override val onUpdate: () -> Unit = {
        store.accept(EmployeesListStore.Intent.LoadEmployees())
    }

    private val router =
        router<EmployeesListHost.Configuration, EmployeesListHost.Child>(initialConfiguration = EmployeesListHost.Configuration.Loading,
            childFactory = this::createChild)


    private fun createChild(
        configuration: EmployeesListHost.Configuration,
        componentContext: ComponentContext,
    ): EmployeesListHost.Child {
        return when (configuration) {
            is EmployeesListHost.Configuration.EmployeesList ->
                EmployeesListHost.Child.EmployeesList(EmployeeListComponent(
                    employee = configuration.employees,
                    onUpdate = onUpdate
                ))
            is EmployeesListHost.Configuration.Loading ->
                EmployeesListHost.Child.Loading(LoadingComponent(R.string.customers_loading))
            is EmployeesListHost.Configuration.UnknownError ->
                EmployeesListHost.Child.Error(ErrorComponent(
                    message = R.string.unknwon_error,
                    icon = R.drawable.error,
                    onContinue = onUpdate
                ))
        }
    }

    override val routerState: Value<RouterState<EmployeesListHost.Configuration, EmployeesListHost.Child>>
        get() = router.state

}