package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Card(
    val rawToken: String,
    val id: Long,
    val history: CardHistory = CardHistory(0, emptyList()),
): Parcelable