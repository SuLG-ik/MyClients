package ru.shafran.network.data.card

import kotlinx.serialization.Serializable


@Serializable
data class ActivationData(
    val cost: Int,
    val capacity: Int,
    val type: CardType = CardType(),
    val note: String = "",
)