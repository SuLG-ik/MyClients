package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CardType(
    val type: String = "Другое",
    val icon: String? = null,
) : Parcelable