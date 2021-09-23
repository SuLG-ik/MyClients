package ru.shafran.network.data.card

import kotlinx.serialization.Serializable

@Serializable
data class CardType(
    val type: String = "Другое",
    val icon: String? = null,
)