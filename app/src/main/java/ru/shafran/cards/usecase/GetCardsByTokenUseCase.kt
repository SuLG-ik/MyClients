package ru.shafran.cards.usecase

import ru.shafran.cards.data.card.Card
import ru.shafran.cards.repository.CardsRepository
import ru.sulgik.common.UseCase

class GetCardsByTokenUseCase(private val cardsRepository: CardsRepository) : UseCase<Card, String> {
    override suspend fun run(parameter: String): Card {
        return cardsRepository.getCardByToken(parameter)
    }
}