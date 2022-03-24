package ru.shafran.common.employees.picker.employee

import ru.shafran.network.employees.data.Employee

class EmployeesListSelectorComponent(
    override val selectedEmployee: Employee?,
    override val employees: List<Employee>,
    private val onSelect: (Employee) -> Unit,
) : EmployeesListSelector {
    override fun onSelect(employee: Employee) {
        onSelect.invoke(employee)
    }
}