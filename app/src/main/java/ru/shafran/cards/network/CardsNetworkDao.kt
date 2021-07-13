package ru.shafran.cards.network

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.card.Card

interface CardsNetworkDao {

    suspend fun getCardByToken(token: String): Card

}