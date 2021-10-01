package ru.shafran.network.card

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.shafran.network.data.card.*
import ru.shafran.network.data.excpetion.CardCantBeUsedException
import ru.shafran.network.data.excpetion.NotFoundException


@Suppress("FunctionName")
fun RemoteCardsRepository(
    httpClient: HttpClient,
): CardsRepository {
    return KtorCardsRepository(
        httpClient = httpClient,
    )
}

internal class KtorCardsRepository(
    private val httpClient: HttpClient,
) : CardsRepository {
    override suspend fun getCardByToken(token: String): Card? {
        return try {
            httpClient.get {
                url.encodedPath = "/cards/getByToken/$token"
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                HttpStatusCode.NotFound -> null
                else -> throw e
            }
        }

    }

    override suspend fun getCardByActivationId(activationId: Long): Card? {
        return try {
            httpClient.get {
                url.encodedPath = "/cards/getByActivation/$activationId"
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                else -> throw e
            }
        }
    }

    override suspend fun getUsagesHistory(): List<CardAction.Usage> {
        return try {
            httpClient.get {
                url.encodedPath = "/cards/history"
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                else -> throw e
            }
        }
    }

    override suspend fun getCardById(cardId: Long): Card? {
        return try {
            httpClient.get {
                url.encodedPath = "/cards/getById/$cardId"
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                HttpStatusCode.NotFound -> null
                else -> throw e
            }
        }
    }

    override suspend fun activateCardById(cardId: Long, data: ActivationData): Card {
        return try {
            httpClient.post {
                url.encodedPath = "/cards/$cardId/activate"
                body = data
                contentType(ContentType.Application.Json)
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                HttpStatusCode.NotFound -> throw NotFoundException()
                else -> throw e
            }
        }
    }

    override suspend fun useCardById(cardId: Long, data: UsageData): Card {
        return try {
            httpClient.post {
                url.encodedPath = "/cards/$cardId/use"
                body = data
                contentType(ContentType.Application.Json)
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                HttpStatusCode.NotFound -> throw NotFoundException()
                HttpStatusCode.Conflict -> throw CardCantBeUsedException()
                else -> throw e
            }
        }
    }

    override suspend fun deactivateCardById(cardId: Long, data: DeactivationData): Card {
        return try {
            httpClient.post {
                url.encodedPath = "/cards/$cardId/deactivate"
                body = data
                contentType(ContentType.Application.Json)
            }
        } catch (e: ResponseException) {
            when (val status = e.response.status) {
                HttpStatusCode.NotFound -> throw NotFoundException()
                HttpStatusCode.Conflict -> throw CardCantBeUsedException()
                else -> throw e
            }
        }
    }
}