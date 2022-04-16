package ru.shafran.common.employees.details.info

import ru.shafran.network.employees.data.Employee

interface EmployeeInfo {

    val employee: Employee

    val onBack: (() -> Unit)?

}