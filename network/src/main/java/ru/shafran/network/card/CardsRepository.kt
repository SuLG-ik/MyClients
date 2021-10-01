package ru.shafran.network.card

import ru.shafran.network.data.card.*

interface CardsRepository {

    suspend fun getCardByToken(token: String): Card?

    suspend fun getCardByActivationId(activationId: Long): Card?

    suspend fun getCardById(cardId: Long): Card?

    suspend fun activateCardById(cardId: Long, data: ActivationData): Card

    suspend fun useCardById(cardId: Long, data: UsageData): Card

    suspend fun getUsagesHistory(): List<CardAction>

    suspend fun deactivateCardById(cardId: Long, data: DeactivationData): Card

}