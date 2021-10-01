package ru.shafran.cards.ui.component.cardsdetails.deactivate

import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.DeactivationDataModel

class CardDeactivationComponent(
    override val card: CardModel,
    private val onDeactivate: (DeactivationDataModel) -> Unit,
    private val onCancel: () -> Unit,
) :
    CardDeactivation {
    override fun onDeactivate(data: DeactivationDataModel) {
        onDeactivate.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }
}