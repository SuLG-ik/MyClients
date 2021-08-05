package ru.shafran.cards.usecase

import ru.shafran.cards.data.card.DeactivationData

data class DeactivateCardByIdParam(
    val id: Int,
    val data: DeactivationData,
)