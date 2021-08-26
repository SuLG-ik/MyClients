package ru.shafran.cards.usecase.cards.param

import ru.shafran.cards.data.card.ActivationData

data class ActivateCardByIdParam (
    val id: Long,
    val data: ActivationData
        )