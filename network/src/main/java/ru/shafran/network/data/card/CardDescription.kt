@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.network.data.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.network.utils.ZonedDateTimeSerializer

@Serializable
sealed class CardDescription {

    @Serializable
    @SerialName("activated")
    data class Activated(val activation: CardAction.Activation) :
        CardDescription()

    @Serializable
    @SerialName("overuse")
    data class Overuse(
        val activation: CardAction.Activation,
    ) : CardDescription()

    @Serializable
    @SerialName("deactivated")
    data class Deactivated(
        val activation: CardAction.Activation,
        val deactivation: CardAction.Deactivation,
    ) : CardDescription()

    @Serializable
    @SerialName("newer_used")
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