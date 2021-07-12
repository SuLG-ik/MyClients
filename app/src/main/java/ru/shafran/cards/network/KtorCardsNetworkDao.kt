package ru.shafran.cards.network

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.shafran.cards.data.card.Card

class KtorCardsNetworkDao(private val client: HttpClient, private val config: NetworkConfig) : CardsNetworkDao{
    override fun getCardByToken(token: String): Flow<Card> {
        return flow {
            client
            emit(client.get(config.buildUrl("/cards?token=$token")))
        }
    }

}