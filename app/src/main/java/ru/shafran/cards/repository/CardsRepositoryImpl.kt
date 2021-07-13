package ru.shafran.cards.repository

import android.util.Log
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.network.CardsNetworkDao

class CardsRepositoryImpl(private val cardsNetwork: CardsNetworkDao) : CardsRepository {
    override suspend fun getCardByToken(token: String): Card {
        return cardsNetwork.getCardByToken(token)
    }
}