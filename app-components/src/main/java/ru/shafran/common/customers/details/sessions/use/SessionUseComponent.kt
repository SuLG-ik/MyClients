package ru.shafran.common.customers.details.sessions.use

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.common.employees.picker.EmployeePickerComponent
import ru.shafran.network.session.data.Session
import ru.shafran.network.session.data.UseSessionRequest

class SessionUseComponent(
    componentContext: ComponentContext,
    override val session: Session,
    private val onUse: (UseSessionRequest) -> Unit,
    private val onBack: () -> Unit,
) : SessionUse, ComponentContext by componentContext {

    override val employeePicker: EmployeePicker =
        EmployeePickerComponent(childContext("employee_picker"))

    override fun onUse(note: String) {
        val employee = employeePicker.selectedEmployee.value
        if (employee != null) {
            onUse.invoke(UseSessionRequest(
                sessionId = session.id,
                employeeId = employee.id,
                note = note,
            ))
        }
    }

    override fun onBack() {
        onBack.invoke()
    }

}