package ru.shafran.cards.repository

import ru.shafran.cards.data.card.ActivationData
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DeactivationData
import ru.shafran.cards.data.card.UsageData
import ru.shafran.cards.network.CardsNetworkDao

class CardsRepositoryImpl(private val cardsNetwork: CardsNetworkDao) : CardsRepository {
    override suspend fun getCardByToken(token: String): Card {
        return cardsNetwork.getCardByToken(token)
    }

    override suspend fun useCardById(id: Int, data: UsageData) {
        return cardsNetwork.useCardById(id, data)
    }

    override suspend fun activateCardById(id: Int, data: ActivationData) {
        return cardsNetwork.activateCardById(id, data)
    }

    override suspend fun deactivateCardById(id: Int, data: DeactivationData) {
        return cardsNetwork.deactivateCardById(id, data)
    }

}