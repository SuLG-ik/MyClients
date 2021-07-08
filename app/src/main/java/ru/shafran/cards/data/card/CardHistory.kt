package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CardHistory(
    val size: Long,
    val actions: List<CardAction>
): Parcelable