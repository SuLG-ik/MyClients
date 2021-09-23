package ru.shafran.network.employee

import ru.shafran.network.data.employee.Employee
import ru.shafran.network.data.employee.EmployeeData

interface EmployeesRepository {

    suspend fun getAllEmployees(): List<Employee>

    suspend fun getEmployeeById(id: Long): Employee?

    suspend fun updateEmployeeById(id: Long, data: EmployeeData): Employee?

    suspend fun createEmployee(data: EmployeeData): Employee

}