package ru.shafran.network.employee

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import ru.shafran.network.data.employee.Employee
import ru.shafran.network.data.employee.EmployeeData

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
        return client.get("/employees")
    }

    override suspend fun getEmployeeById(id: Long): Employee {
        return client.get("/employees/${id}")
    }

    override suspend fun updateEmployeeById(id: Long, data: EmployeeData): Employee {
        return client.put("/employees/${id}")
    }

    override suspend fun createEmployee(data: EmployeeData): Employee {
        return client.post("/employees") {
            contentType(ContentType.Application.Json)
            body = data
        }
    }

    override suspend fun addImageToEmployee(employeeId: Long, image: ByteArray): Employee {
        return client.post("/employees/image") {
                formData {
                    append("image", image)
                }
            }
    }
}
