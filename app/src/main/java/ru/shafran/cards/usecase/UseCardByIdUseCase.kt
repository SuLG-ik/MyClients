package ru.shafran.cards.usecase

import ru.shafran.cards.repository.CardsRepository
import ru.sulgik.common.UseCase

class UseCardByIdUseCase(private val cardsRepository: CardsRepository) :
    UseCase<Unit, UseCardByIdParam> {

    override suspend fun run(parameter: UseCardByIdParam) {
        cardsRepository.useCardById(parameter.id, parameter.data)
    }


}