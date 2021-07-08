package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CardType(
    val type: String = "Другое",
    val icon: String? = null
): Parcelable