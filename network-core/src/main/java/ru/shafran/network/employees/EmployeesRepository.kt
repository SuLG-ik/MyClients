package ru.shafran.network.employees

import ru.shafran.network.employees.data.*


interface EmployeesRepository {

    suspend fun createEmployee(data: CreateEmployeeRequest): CreateEmployeeResponse
    suspend fun getAllEmployees(data: GetAllEmployeesRequest): GetAllEmployeesResponse
    suspend fun layoffEmployee(data: LayoffEmployeeRequest): LayoffEmployeeResponse
    suspend fun getEmployeeById(data: GetEmployeeByIdRequest): GetEmployeeByIdResponse
    suspend fun addEmployeeImage(data: AddEmployeeImageRequest): AddEmployeeImageResponse

}