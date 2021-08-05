package ru.shafran.cards.usecase

import ru.shafran.cards.repository.CardsRepository
import ru.sulgik.common.UseCase

class DeactivateCardByIdUseCase(private val cardsRepository: CardsRepository) :
    UseCase<Unit, DeactivateCardByIdParam> {

    override suspend fun run(parameter: DeactivateCardByIdParam) {
        cardsRepository.deactivateCardById(parameter.id, parameter.data)
    }


}