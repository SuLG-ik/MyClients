package ru.shafran.cards.usecase

import ru.shafran.cards.data.card.ActivationData

data class ActivateCardByIdParam (
    val id: Int,
    val data: ActivationData
        )