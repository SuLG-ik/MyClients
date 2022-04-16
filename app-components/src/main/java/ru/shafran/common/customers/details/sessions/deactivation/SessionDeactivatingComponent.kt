package ru.shafran.common.customers.details.sessions.deactivation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.common.employees.picker.EmployeePickerComponent
import ru.shafran.network.session.data.DeactivateSessionRequestData
import ru.shafran.network.session.data.Session

class SessionDeactivatingComponent(
    componentContext: ComponentContext,
    override val session: Session,
    override val data: DeactivateSessionRequestData?,
    override val onApply: (DeactivateSessionRequestData) -> Unit,
    override val onBack: () -> Unit,
) : SessionDeactivating, ComponentContext by componentContext {

    override val employeePicker: EmployeePicker =
        EmployeePickerComponent(childContext("employee_picker"))

}