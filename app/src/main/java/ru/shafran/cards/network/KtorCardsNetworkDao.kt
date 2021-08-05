package ru.shafran.cards.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.shafran.cards.data.card.ActivationData
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DeactivationData
import ru.shafran.cards.data.card.UsageData

class KtorCardsNetworkDao(private val client: HttpClient, private val config: NetworkConfig) :
    CardsNetworkDao {

    override suspend fun getCardByToken(token: String): Card {
        return client.get(config.buildUrl("/cards?token=$token"))
    }

    override suspend fun useCardById(id: Int, data: UsageData) {
        return client.post(config.buildUrl("/cards/$id/use")) {
            contentType(ContentType.Application.Json)
            body = data
        }
    }

    override suspend fun activateCardById(id: Int, data: ActivationData) {
        return client.post(config.buildUrl("/cards/$id/activate")) {
            contentType(ContentType.Application.Json)
            body = data
        }
    }

    override suspend fun deactivateCardById(id: Int, data: DeactivationData) {
        return client.post(config.buildUrl("/cards/$id/deactivate")) {
            contentType(ContentType.Application.Json)
            body = data
        }
    }


}