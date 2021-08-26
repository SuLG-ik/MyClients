package ru.shafran.cards.usecase.cards.param

import ru.shafran.cards.data.card.UsageData

data class UseCardByIdParam(
    val id: Long,
    val data: UsageData,
)