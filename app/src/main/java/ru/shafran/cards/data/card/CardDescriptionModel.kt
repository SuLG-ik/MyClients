package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class CardDescriptionModel : Parcelable {

    @Parcelize
    data class Activated(val activation: CardActionModel.Activation) :
        CardDescriptionModel()

    @Parcelize
    data class Overuse(
        val activation: CardActionModel.Activation,
    ) : CardDescriptionModel()

    @Parcelize
    data class Deactivated(
        val activation: CardActionModel.Activation,
        val deactivation: CardActionModel.Deactivation,
    ) : CardDescriptionModel()

    @Parcelize
    object NewerUsed : CardDescriptionModel()

}

val CardModel.description: CardDescriptionModel
    get() {
        if (history.size == 0) return CardDescriptionModel.NewerUsed
        val lastActivation =
            history.actions.last { it is CardActionModel.Activation } as CardActionModel.Activation
        val lastDeactivation =
            history.actions.lastOrNull { it is CardActionModel.Deactivation && it.activationId == lastActivation.id } as CardActionModel.Deactivation?
        if (lastDeactivation != null) return CardDescriptionModel.Deactivated(
            activation = lastActivation,
            deactivation = lastDeactivation
        )
        val pairedUsages =
            history.actions.filter { it is CardActionModel.Usage && it.activationId == lastActivation.id }
        if (pairedUsages.size >= lastActivation.data.capacity)
            return CardDescriptionModel.Overuse(lastActivation)
        return CardDescriptionModel.Activated(lastActivation)
    }