package ru.shafran.cards

import ru.shafran.cards.data.card.Card
import ru.shafran.cards.repository.CardsRepository
import ru.sulgik.common.UseCase

class GetCardsByTokenUseCase(val cardsRepository: CardsRepository) : UseCase<Card, String> {

    override suspend fun run(parameter: String): Card {
        return cardsRepository.getCardByToken(parameter)
    }
}