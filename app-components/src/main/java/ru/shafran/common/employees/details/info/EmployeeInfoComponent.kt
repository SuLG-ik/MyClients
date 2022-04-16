package ru.shafran.common.employees.details.info

import ru.shafran.network.employees.data.Employee

class EmployeeInfoComponent(
    override val employee: Employee,
    override val onBack: (() -> Unit)? = null,
) : EmployeeInfo