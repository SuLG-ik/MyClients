package ru.shafran.network.data.employee

import kotlinx.serialization.Serializable


@Serializable
data class Employee(
    val id: Long,
    val data: EmployeeData,
)