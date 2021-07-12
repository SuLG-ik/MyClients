package ru.shafran.cards.network

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.card.Card

interface CardsNetworkDao {

    fun getCardByToken(token: String): Flow<Card>

}