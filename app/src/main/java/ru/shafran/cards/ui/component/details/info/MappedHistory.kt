package ru.shafran.cards.ui.component.details.info

import ru.shafran.cards.data.card.CardActionModel


class MappedHistory(
    val activation: CardActionModel.Activation,
    val usages: List<CardActionModel.Usage>,
    val deactivation: CardActionModel.Deactivation?
)

fun List<CardActionModel>.toMappedHistory(): List<MappedHistory> {
    val activations = filterIsInstance<CardActionModel.Activation>()
    val usages = filterIsInstance<CardActionModel.Usage>()
    val deactivations = filterIsInstance<CardActionModel.Deactivation>()
    return activations.map { activation ->
        MappedHistory(
            activation = activation,
            usages = usages.filter { it.activationId == activation.id },
            deactivation = deactivations.firstOrNull { it.activationId == activation.id }
        )
    }
}