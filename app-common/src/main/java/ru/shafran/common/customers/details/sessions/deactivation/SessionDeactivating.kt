package ru.shafran.common.customers.details.sessions.deactivation

import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.network.session.data.DeactivateSessionRequestData
import ru.shafran.network.session.data.Session

interface SessionDeactivating {

    val employeePicker:EmployeePicker

    val session: Session

    val data: DeactivateSessionRequestData?

    val onApply: (DeactivateSessionRequestData) -> Unit

    val onBack: () -> Unit

}