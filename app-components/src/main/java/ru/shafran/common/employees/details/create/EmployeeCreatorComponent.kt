package ru.shafran.common.employees.details.create

import ru.shafran.network.employees.data.CreateEmployeeRequestData

class EmployeeCreatorComponent(
    override val data: CreateEmployeeRequestData?,
    override val onApply: (CreateEmployeeRequestData) -> Unit,
) : EmployeeCreator