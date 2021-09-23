package ru.shafran.network.data.card

import kotlinx.serialization.Serializable

@Serializable
data class CardHistory(
    val size: Int,
    val actions: List<CardAction>,
)