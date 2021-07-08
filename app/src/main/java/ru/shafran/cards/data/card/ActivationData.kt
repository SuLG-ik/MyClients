package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ActivationData(val type: CardType = CardType(), val note: String = ""): Parcelable
