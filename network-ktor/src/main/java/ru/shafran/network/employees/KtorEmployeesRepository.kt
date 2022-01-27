package ru.shafran.network.employees

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.shafran.network.employees.data.*


internal class KtorEmployeesRepository(
    private val httpClient: HttpClient,
    private val json: Json
):  EmployeesRepository {
    override suspend fun getAllEmployees(data: GetAllEmployeesRequest): GetAllEmployeesResponse {
        return httpClient.get(
            path = "/employees/getAllEmployees",
            body = data,
        )
    }

    override suspend fun createEmployee(data: CreateEmployeeRequest): CreateEmployeeResponse {
        return httpClient.post(
            path = "/employees/createEmployee",
            body = data,
        )
    }

    override suspend fun layoffEmployee(data: LayoffEmployeeRequest): LayoffEmployeeResponse {
        return httpClient.delete(
            path = "/employees/layoffEmployee",
            body = data,
        )
    }

    override suspend fun getEmployeeById(data: GetEmployeeByIdRequest): GetEmployeeByIdResponse {
        return httpClient.get(
            path = "/employees/getEmployeeById",
            body = data,
        )
    }

    override suspend fun addEmployeeImage(data: AddEmployeeImageRequest): AddEmployeeImageResponse {
        TODO("Image")
        return httpClient.post(
            path = "/employees/addEmployeeImage",
            body = MultiPartFormDataContent(
                formData {
                    append("value", json.encodeToString(data.data))
                }
            )
        )
    }
}