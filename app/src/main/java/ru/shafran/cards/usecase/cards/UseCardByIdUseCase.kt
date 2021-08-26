package ru.shafran.cards.usecase.cards

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.shafran.cards.data.excpetion.CardCantBeUsedException
import ru.shafran.cards.data.excpetion.InternalServerErrorException
import ru.shafran.cards.data.excpetion.NotFoundException
import ru.shafran.cards.data.excpetion.ServerDoesNotResponseException
import ru.shafran.cards.network.NetworkConfig
import ru.shafran.cards.usecase.cards.param.UseCardByIdParam
import ru.sulgik.common.UseCase
import java.net.ConnectException

class UseCardByIdUseCase(
    private val client: HttpClient,
    private val config: NetworkConfig,
) : UseCase<Unit, UseCardByIdParam> {

    override suspend fun run(parameter: UseCardByIdParam) {
        val response = try {
            client.post<HttpResponse>(config.buildUrl("/cards/${parameter.id}/use")) {
                contentType(ContentType.Application.Json)
                body = parameter.data
            }
        } catch (e: ConnectException) {
            throw ServerDoesNotResponseException()
        }
        when (response.status) {
            HttpStatusCode.OK ->
                return
            HttpStatusCode.NotFound ->
                throw NotFoundException()
            HttpStatusCode.Conflict ->
                throw CardCantBeUsedException()
            HttpStatusCode.InternalServerError ->
                throw InternalServerErrorException()
            else ->
                throw Exception(message = response.toString())
        }
    }


}