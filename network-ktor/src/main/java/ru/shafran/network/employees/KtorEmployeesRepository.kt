package ru.shafran.network.employees

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import ru.shafran.network.employees.data.AddEmployeeImageRequest
import ru.shafran.network.employees.data.AddEmployeeImageResponse
import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.CreateEmployeeResponse
import ru.shafran.network.employees.data.GetAllEmployeesRequest
import ru.shafran.network.employees.data.GetAllEmployeesResponse
import ru.shafran.network.employees.data.GetEmployeeByIdRequest
import ru.shafran.network.employees.data.GetEmployeeByIdResponse
import ru.shafran.network.employees.data.LayoffEmployeeRequest
import ru.shafran.network.employees.data.LayoffEmployeeResponse


internal class KtorEmployeesRepository(
    private val httpClient: HttpClient,
    private val json: Json,
) : EmployeesRepository {
    override suspend fun getAllEmployees(data: GetAllEmployeesRequest): GetAllEmployeesResponse {
        return httpClient.get("employees/getAllEmployees") {
            setBody(data)
        }.body()
    }

    override suspend fun createEmployee(data: CreateEmployeeRequest): CreateEmployeeResponse {
        return httpClient.post("employees/createEmployee") {
            setBody(data)
        }.body()
    }

    override suspend fun layoffEmployee(data: LayoffEmployeeRequest): LayoffEmployeeResponse {
        return httpClient.delete("employees/layoffEmployee") {
            setBody(data)
        }.body()
    }

    override suspend fun getEmployeeById(data: GetEmployeeByIdRequest): GetEmployeeByIdResponse {
        return httpClient.get("employees/getEmployeeById") {
            setBody(data)
        }.body()
    }

    override suspend fun addEmployeeImage(data: AddEmployeeImageRequest): AddEmployeeImageResponse {
        TODO("Image")
    }
}