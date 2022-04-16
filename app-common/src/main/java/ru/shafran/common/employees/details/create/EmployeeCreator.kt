package ru.shafran.common.employees.details.create

import ru.shafran.network.employees.data.CreateEmployeeRequestData

interface EmployeeCreator {
    val data: CreateEmployeeRequestData?
    val onApply: (CreateEmployeeRequestData) -> Unit
}