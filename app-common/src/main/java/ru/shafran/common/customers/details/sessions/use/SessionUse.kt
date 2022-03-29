package ru.shafran.common.customers.details.sessions.use

import ru.shafran.common.employees.picker.EmployeePicker
import ru.shafran.network.session.data.Session

interface SessionUse {

    val employeePicker: EmployeePicker

    val session: Session

    fun onUse(note: String)

    fun onBack()

}