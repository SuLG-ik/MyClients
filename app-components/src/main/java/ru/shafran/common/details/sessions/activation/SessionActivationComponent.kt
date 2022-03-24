package ru.shafran.common.details.sessions.activation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.common.employees.picker.EmployeePickerComponent
import ru.shafran.common.services.picker.ConfiguredServicePicker
import ru.shafran.common.services.picker.ConfiguredServicePickerComponent
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.CreateSessionForCustomerRequest

internal class SessionActivationComponent(
    componentContext: ComponentContext,
    override val customer: Customer.ActivatedCustomer,
    private val onActivate: (CreateSessionForCustomerRequest) -> Unit,
    private val onBack: () -> Unit,
) : SessionActivation, ComponentContext by componentContext {
    override val servicePicker: ConfiguredServicePicker =
        ConfiguredServicePickerComponent(childContext("service_picker"))
    override val employeePicker: EmployeePicker =
        EmployeePickerComponent(childContext("employees_picker"))

    override fun onActivate(request: CreateSessionForCustomerRequest) {
        onActivate.invoke(request)
    }

    override fun onBack() {
        onBack.invoke()
    }


}