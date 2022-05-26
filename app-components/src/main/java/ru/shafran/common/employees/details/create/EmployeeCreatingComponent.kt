package ru.shafran.common.employees.details.create

import ru.shafran.network.employees.data.CreateEmployeeRequestData

class EmployeeCreatingComponent(
    data: CreateEmployeeRequestData?,
    onApply: (CreateEmployeeRequestData) -> Unit,
) : EmployeeCreating {
    override val creator: EmployeeCreator = EmployeeCreatorComponent(
        data = data,
        onApply = { onApply(it) },
    )
}