package ru.shafran.network.employees

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import ru.shafran.network.employees.data.AddEmployeeImageRequest
import ru.shafran.network.employees.data.AddEmployeeImageResponse
import ru.shafran.network.employees.data.CreateEmployeeRequest
import ru.shafran.network.employees.data.CreateEmployeeResponse
import ru.shafran.network.employees.data.GetAllEmployeesRequest
import ru.shafran.network.employees.data.GetAllEmployeesResponse
import ru.shafran.network.employees.data.GetEmployeeByIdResponse
import ru.shafran.network.employees.data.GetEmployeeWithIdRequest
import ru.shafran.network.employees.data.LayoffEmployeeRequest
import ru.shafran.network.employees.data.LayoffEmployeeResponse
import ru.shafran.network.tryRequest


internal class KtorEmployeesRepository(
    private val httpClient: HttpClient,
    private val json: Json,
) : EmployeesRepository {
    override suspend fun getAllEmployees(data: GetAllEmployeesRequest): GetAllEmployeesResponse {
        return tryRequest {
            httpClient.get("employees/getAllEmployees") {
                setBody(data)
            }
        }
    }

    override suspend fun createEmployee(data: CreateEmployeeRequest): CreateEmployeeResponse {
        return tryRequest {
            httpClient.post("employees/createEmployee") {
                setBody(data)
            }
        }
    }

    override suspend fun layoffEmployee(data: LayoffEmployeeRequest): LayoffEmployeeResponse {
        return tryRequest {
            httpClient.delete("employees/layoffEmployee") {
                setBody(data)
            }
        }
    }
    override suspend fun getEmployeeById(data: GetEmployeeWithIdRequest): GetEmployeeByIdResponse {
        return tryRequest {
            httpClient.get("employees/getEmployeeById") {
                setBody(data)
            }
        }
    }
    override suspend fun addEmployeeImage(data: AddEmployeeImageRequest): AddEmployeeImageResponse {
        TODO("Image")
    }
}