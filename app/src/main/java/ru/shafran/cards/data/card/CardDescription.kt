@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.cards.utils.ZonedDateTimeSerializer

@Serializable
sealed class CardDescription: Parcelable {

    @Serializable
    @SerialName("activated")
    @Parcelize
    data class Activated(val activation: CardAction.Activation = CardAction.Activation()): CardDescription()

    @Serializable
    @Parcelize
    @SerialName("deactivated")
    data class Deactivated(
        val activation: CardAction.Activation,
        val deactivation: CardAction.Deactivation
    ): CardDescription()

    @Serializable
    @SerialName("newer_used")
    @Parcelize
    object NewerUsed: CardDescription()

}