package ru.shafran.cards.usecase

import ru.shafran.cards.data.card.UsageData

data class UseCardByIdParam(
    val id: Int,
    val data: UsageData,
)