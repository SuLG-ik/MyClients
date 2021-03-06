package ru.shafran.common.employees.list

import ru.shafran.common.utils.Updatable
import ru.shafran.network.employees.data.Employee

interface EmployeesList : Updatable {

    val onCreateEmployee: () -> Unit

    val onSelect: (Employee) -> Unit

    val employee: List<Employee>

    override val onUpdate: (() -> Unit)
}