package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.Card

@Parcelize
data class CardModel(
    val rawToken: String,
    val id: Long,
    val history: CardHistoryModel = CardHistoryModel(0, emptyList()),
) : Parcelable

fun Card.toModel(): CardModel {
    return CardModel(
        rawToken = rawToken,
        id = id,
        history = history.toModel(),
    )
}

fun CardModel.toData(): Card {
    return Card(
        rawToken = rawToken,
        id = id,
        history = history.toData()
    )
}

val CardModel.lastActivation: CardActionModel.Activation?
    get() {
        return history.actions.filterIsInstance<CardActionModel.Activation>()
            .maxByOrNull { it.time }
    }

val CardModel.description: CardDescriptionModel
    get() {
        val lastActivation = lastActivation ?: return CardDescriptionModel.NewerUsed

        val deactivation = history.actions.firstOrNull {
            it is CardActionModel.Deactivation && it.activationId == lastActivation.id
        } as CardActionModel.Deactivation?
        if (deactivation != null)
            return CardDescriptionModel.Deactivated(lastActivation, deactivation)

        val usages =
            history.actions.filter { it is CardActionModel.Usage && it.activationId == lastActivation.id }
        if (usages.size >= lastActivation.data.capacity)
            return CardDescriptionModel.Overuse(lastActivation)

        return CardDescriptionModel.Activated(lastActivation)
    }
