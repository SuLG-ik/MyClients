package ru.shafran.network.card

import ru.shafran.network.data.card.ActivationData
import ru.shafran.network.data.card.Card
import ru.shafran.network.data.card.DeactivationData
import ru.shafran.network.data.card.UsageData

interface CardsRepository {

    suspend fun getCardByToken(token: String): Card?

    suspend fun getCardById(cardId: Long): Card?

    suspend fun activateCardById(cardId: Long, data: ActivationData): Card

    suspend fun useCardById(cardId: Long, data: UsageData): Card

    suspend fun deactivateCardById(cardId: Long, data: DeactivationData): Card

}