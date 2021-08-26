@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.cards.utils.ZonedDateTimeSerializer

@Serializable
sealed class CardDescription : Parcelable {

    @Serializable
    @SerialName("activated")
    @Parcelize
    data class Activated(val activation: CardAction.Activation) :
        CardDescription()

    @Serializable
    @Parcelize
    @SerialName("overuse")
    data class Overuse(
        val activation: CardAction.Activation,
    ) : CardDescription()

    @Serializable
    @Parcelize
    @SerialName("deactivated")
    data class Deactivated(
        val activation: CardAction.Activation,
        val deactivation: CardAction.Deactivation,
    ) : CardDescription()

    @Serializable
    @SerialName("newer_used")
    @Parcelize
    object NewerUsed : CardDescription()

}

val Card.description: CardDescription
    get() {
        if (history.size == 0) return CardDescription.NewerUsed
        val lastActivation =
            history.actions.last { it is CardAction.Activation } as CardAction.Activation
        val lastDeactivation =
            history.actions.lastOrNull { it is CardAction.Deactivation && it.activationId == lastActivation.id } as CardAction.Deactivation?
        if (lastDeactivation != null) return CardDescription.Deactivated(activation = lastActivation,
            deactivation = lastDeactivation)
        val pairedUsages =
            history.actions.filter { it is CardAction.Usage && it.activationId == lastActivation.id }
        if (pairedUsages.size >= lastActivation.data.capacity)
            return CardDescription.Overuse(lastActivation)
        return CardDescription.Activated(lastActivation)
    }