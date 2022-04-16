package ru.shafran.common.employees.details.create

import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.CreateEmployeeRequestData

class EmployeeCreatingComponent(
    data: CreateEmployeeRequestData?,
    onApply: (CreateEmployeeRequest) -> Unit,
) : EmployeeCreating {
    override val creator: EmployeeCreator = EmployeeCreatorComponent(
        data = data,
        onApply = { onApply(CreateEmployeeRequest(it)) },
    )
}