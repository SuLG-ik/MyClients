package ru.shafran.network.data.card

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val rawToken: String,
    val id: Long,
    val history: CardHistory = CardHistory(0, emptyList()),
)