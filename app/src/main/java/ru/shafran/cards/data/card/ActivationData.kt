package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ActivationData(
    val cost: Int,
    val capacity: Int,
    val type: CardType = CardType(),
    val note: String = "",
) : Parcelable