package ru.shafran.cards.usecase.cards.param

import ru.shafran.cards.data.card.DeactivationData

data class DeactivateCardByIdParam(
    val id: Long,
    val data: DeactivationData,
)