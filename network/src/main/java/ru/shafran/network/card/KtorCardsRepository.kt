package ru.shafran.network.card

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.shafran.network.data.card.*


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
    override suspend fun getCardByToken(token: String): Card {
        return httpClient.get(path = "/cards/getByToken/$token")
    }

    override suspend fun getCardByActivationId(activationId: Long): Card {
        return httpClient.get(path = "/cards/getByActivation/$activationId")
    }

    override suspend fun getUsagesHistory(): List<CardAction.Usage> {
        return httpClient.get(path = "/cards/history")
    }

    override suspend fun getCardById(cardId: Long): Card {
        return httpClient.get(path = "/cards/getById/$cardId")
    }

    override suspend fun activateCardById(cardId: Long, data: ActivationData): Card {
        return httpClient.post(path = "/cards/$cardId/activate", body = data) {
                contentType(ContentType.Application.Json)
            }
    }

    override suspend fun useCardById(cardId: Long, data: UsageData): Card {
        return httpClient.post(path = "/cards/$cardId/use", body = data) {
                contentType(ContentType.Application.Json)
            }
    }

    override suspend fun deactivateCardById(cardId: Long, data: DeactivationData): Card {
        return httpClient.post(path = "/cards/$cardId/deactivate" ) {
                body = data
                contentType(ContentType.Application.Json)
            }
    }
}