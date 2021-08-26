package ru.shafran.cards.ui.component.details.deactivate

import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DeactivationData

class CardDeactivationComponent(
    card: Card,
    private val onDeactivate: (DeactivationData) -> Unit,
    private val onCancel: () -> Unit,
) :
    CardDeactivation {

    override val card: MutableStateFlow<Card> = MutableStateFlow(card)

    override fun onDeactivate(data: DeactivationData) {
        onDeactivate.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }
}