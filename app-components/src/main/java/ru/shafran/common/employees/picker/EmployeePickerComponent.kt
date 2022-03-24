package ru.shafran.common.employees.picker

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.common.employees.picker.employee.EmployeeSelectorComponent
import ru.shafran.network.employees.data.Employee


class EmployeePickerComponent(componentContext: ComponentContext) :
    EmployeePicker, ComponentContext by componentContext {

    private val instance = instanceKeeper.getOrCreate { Instance() }

    override val selectedEmployee: MutableStateFlow<Employee?> =
        instance.selectedConfiguration

    override val isPicking: MutableStateFlow<Boolean> = instance.isPicking

    private val router =
        router<EmployeePicker.Configuration, EmployeePicker.Child>(
            initialConfiguration = EmployeePicker.Configuration.EmployeeSelector(selectedEmployee.value),
            childFactory = this::createChild
        )

    override val routerState: Value<RouterState<EmployeePicker.Configuration, EmployeePicker.Child>>
        get() = router.state

    private fun createChild(
        configuration: EmployeePicker.Configuration,
        componentContext: ComponentContext,
    ): EmployeePicker.Child {
        return when (configuration) {
            is EmployeePicker.Configuration.EmployeeSelector ->
                configuration.create(componentContext)
        }
    }

    private fun onSelect(configuredService: Employee) {
        onTogglePicking(false)
        selectedEmployee.value = configuredService
    }

    private fun EmployeePicker.Configuration.EmployeeSelector.create(componentContext: ComponentContext): EmployeePicker.Child {
        return EmployeePicker.Child.EmployeeSelector(
            EmployeeSelectorComponent(
                componentContext = componentContext,
                onSelect = this@EmployeePickerComponent::onSelect,
            )
        )
    }


    override fun onTogglePicking(isPicking: Boolean) {
        this.isPicking.value = isPicking
    }

    private class Instance : InstanceKeeper.Instance {

        val selectedConfiguration: MutableStateFlow<Employee?> = MutableStateFlow(null)

        val isPicking: MutableStateFlow<Boolean> = MutableStateFlow(false)

        override fun onDestroy() {}
    }

}