package ru.shafran.cards.repository

import ru.shafran.cards.data.card.ActivationData
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DeactivationData
import ru.shafran.cards.data.card.UsageData

interface CardsRepository {

    suspend fun getCardByToken(token: String): Card

    suspend fun activateCardById(id: Int, data: ActivationData)
    suspend fun useCardById(id: Int, data: UsageData)
    suspend fun deactivateCardById(id: Int, data: DeactivationData)
}