package ru.shafran.common.employees.list

import ru.shafran.network.employees.data.Employee

class EmployeeListComponent(
    override val employee: List<Employee>,
    override val onUpdate: () -> Unit,
    override val onSelect: (Employee) -> Unit,
    override val onCreateEmployee: () -> Unit,
) : EmployeesList {
}