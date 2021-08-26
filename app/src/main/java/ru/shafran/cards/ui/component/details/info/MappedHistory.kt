package ru.shafran.cards.ui.component.details.info

import ru.shafran.cards.data.card.CardAction

class MappedHistory(
    val activation: CardAction.Activation,
    val usages: List<CardAction.Usage>,
    val deactivation: CardAction.Deactivation?
)

fun List<CardAction>.toMappedHistory(): List<MappedHistory> {
    val activations = filterIsInstance<CardAction.Activation>()
    val usages = filterIsInstance<CardAction.Usage>()
    val deactivations = filterIsInstance<CardAction.Deactivation>()
    return activations.map { activation ->
        MappedHistory(
            activation = activation,
            usages = usages.filter { it.activationId == activation.id },
            deactivation = deactivations.firstOrNull { it.activationId == activation.id }
        )
    }
}