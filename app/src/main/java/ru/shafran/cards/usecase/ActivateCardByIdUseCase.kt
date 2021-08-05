package ru.shafran.cards.usecase

import ru.shafran.cards.repository.CardsRepository
import ru.sulgik.common.UseCase

class ActivateCardByIdUseCase(private val cardsRepository: CardsRepository) :
    UseCase<Unit, ActivateCardByIdParam> {

    override suspend fun run(parameter: ActivateCardByIdParam) {
        cardsRepository.activateCardById(parameter.id, parameter.data)
    }


}