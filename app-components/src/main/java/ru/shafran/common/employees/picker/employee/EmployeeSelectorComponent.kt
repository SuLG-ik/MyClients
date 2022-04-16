package ru.shafran.common.employees.picker.employee

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.replaceAll
import ru.shafran.common.utils.stores
import ru.shafran.network.employees.EmployeesListStore
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.utils.reduceStates

class EmployeeSelectorComponent(
    componentContext: ComponentContext,
    private val onSelect: (Employee) -> Unit,
) : EmployeeSelector, Updatable, ComponentContext by componentContext {

    private val store by stores<EmployeesListStore>()

    private val router = router<EmployeeSelector.Configuration, EmployeeSelector.Child>(
        initialConfiguration = EmployeeSelector.Configuration.Loading,
        childFactory = this::createChild
    )

    override val routerState: Value<RouterState<EmployeeSelector.Configuration, EmployeeSelector.Child>>
        get() = router.state

    private fun createChild(
        configuration: EmployeeSelector.Configuration,
        componentContext: ComponentContext,
    ): EmployeeSelector.Child {
        return when (configuration) {
            is EmployeeSelector.Configuration.Loading ->
                configuration.create()
            is EmployeeSelector.Configuration.EmployeeList ->
                configuration.create()
            is EmployeeSelector.Configuration.UnknownError ->
                configuration.create()
        }
    }

    private fun EmployeeSelector.Configuration.UnknownError.create(): EmployeeSelector.Child {
        return EmployeeSelector.Child.Error(ErrorComponent(
            R.string.unknwon_error,
            R.drawable.error,
            onContinue = onUpdate,
        ))
    }


    private fun EmployeeSelector.Configuration.Loading.create(): EmployeeSelector.Child {
        return EmployeeSelector.Child.Loading(LoadingComponent(R.string.employees_loading))
    }

    private fun EmployeeSelector.Configuration.EmployeeList.create(): EmployeeSelector.Child {
        return EmployeeSelector.Child.EmployeeList(
            EmployeesListSelectorComponent(
                employees = employees,
                selectedEmployee = selectedEmployee,
                onSelect = onSelect,
            )
        )
    }

    init {
        store.reduceStates(this, this::reduceStates)
    }

    private fun reduceStates(state: EmployeesListStore.State) {
        when (state) {
            is EmployeesListStore.State.Loading ->
                router.replaceAll(EmployeeSelector.Configuration.Loading)
            is EmployeesListStore.State.EmployeesLoaded ->
                router.replaceAll(EmployeeSelector.Configuration.EmployeeList(state.employees))
            is EmployeesListStore.State.Error ->
                router.replaceAll(EmployeeSelector.Configuration.UnknownError)
            is EmployeesListStore.State.Empty ->
                onUpdate()
        }
    }

    override val onUpdate: (() -> Unit) = {
        store.accept(EmployeesListStore.Intent.LoadEmployees())
    }

}