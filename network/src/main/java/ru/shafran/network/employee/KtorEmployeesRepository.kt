package ru.shafran.network.employee

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import ru.shafran.network.data.employee.Employee
import ru.shafran.network.data.employee.EmployeeData
import ru.shafran.network.data.excpetion.ServerDoesNotResponseException

@Suppress("FunctionName")
fun RemoteEmployeesRepository(
    client: HttpClient,
): EmployeesRepository {
    return KtorEmployeesRepository(
        client = client,
    )
}


internal class KtorEmployeesRepository(
    private val client: HttpClient,
) : EmployeesRepository {


    override suspend fun getAllEmployees(): List<Employee> {
        return try {
            val employees = client.get<List<Employee>>("/employees/")
            employees
        } catch (responce: ResponseException) {
            throw ServerDoesNotResponseException(
                responce.response.status.value
            )
        }
    }

    override suspend fun getEmployeeById(id: Long): Employee? {
        return try {
            client.get("/employees/${id}")
        } catch (responce: ResponseException) {
            return when (val status = responce.response.status) {
                HttpStatusCode.NotFound -> {
                    null
                }
                else -> {
                    throw ServerDoesNotResponseException(
                        status.value
                    )
                }
            }
        }
    }

    override suspend fun updateEmployeeById(id: Long, data: EmployeeData): Employee? {
        return try {
            client.put("/employees/${id}")
        } catch (responce: ResponseException) {
            return when (val status = responce.response.status) {
                HttpStatusCode.NotFound -> {
                    null
                }
                else -> {
                    throw ServerDoesNotResponseException(
                        status.value
                    )
                }
            }
        }
    }

    override suspend fun createEmployee(data: EmployeeData): Employee {
        return try {
            client.post("/employees") {
                contentType(ContentType.Application.Json)
                body = data
            }
        } catch (responce: ResponseException) {
            throw ServerDoesNotResponseException(
                responce.response.status.value
            )
        }
    }

    override suspend fun addImageToEmployee(employeeId: Long, image: ByteArray): Employee {
        return try {
            client.post("/employees/image"){
                formData {
                    append("image", image)
                }
            }
        } catch(e : Exception) {
            throw e
        }
    }
}
