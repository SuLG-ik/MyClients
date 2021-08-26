package ru.shafran.cards.usecase.employees

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.shafran.cards.data.employee.Employee
import ru.shafran.cards.data.excpetion.InternalServerErrorException
import ru.shafran.cards.data.excpetion.NotFoundException
import ru.shafran.cards.data.excpetion.ServerDoesNotResponseException
import ru.shafran.cards.network.NetworkConfig
import ru.sulgik.common.UseCase
import java.net.ConnectException

class GetEmployeeByIdUseCase(
    private val client: HttpClient,
    private val config: NetworkConfig,
) : UseCase<Employee, Long> {
    override suspend fun run(parameter: Long): Employee {
        val response = try {
            client.get<HttpResponse>(config.buildUrl("/employees/$parameter"))
        } catch (e: ConnectException) {
            throw ServerDoesNotResponseException()
        }
        when (response.status) {
            HttpStatusCode.OK ->
                return response.receive()
            HttpStatusCode.NotFound ->
                throw NotFoundException()
            HttpStatusCode.InternalServerError ->
                throw InternalServerErrorException()
            else ->
                throw Exception(message = response.toString())
        }
    }
}