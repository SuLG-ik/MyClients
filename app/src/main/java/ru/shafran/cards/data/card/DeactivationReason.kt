package ru.shafran.cards.data.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DeactivationReason {
    @SerialName("manual")
    MANUAL,
    @SerialName("overuse")
    OVERUSE,
}