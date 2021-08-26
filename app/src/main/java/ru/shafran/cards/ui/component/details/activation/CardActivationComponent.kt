package ru.shafran.cards.ui.component.details.activation

import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.ActivationData
import ru.shafran.cards.data.card.Card

class CardActivationComponent(
    card: Card,
    private val onActivate: (ActivationData) -> Unit,
    private val onCancel: () -> Unit,
) : CardActivation {

    override val card: MutableStateFlow<Card> = MutableStateFlow(card)

    override fun onActivate(data: ActivationData) {
        onActivate.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }

}