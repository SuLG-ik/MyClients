package ru.shafran.cards.network

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.card.ActivationData
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DeactivationData
import ru.shafran.cards.data.card.UsageData

interface CardsNetworkDao {

    suspend fun getCardByToken(token: String): Card

    suspend fun useCardById(id: Int, data: UsageData)
    suspend fun activateCardById(id: Int, data: ActivationData)
    suspend fun deactivateCardById(id: Int, data: DeactivationData)
}