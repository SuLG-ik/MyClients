package ru.shafran.cards.ui.component.cardsdetails.activation

import ru.shafran.cards.data.card.ActivationDataModel
import ru.shafran.cards.data.card.CardModel

class CardActivationComponent(
    override val card: CardModel,
    private val onActivate: (ActivationDataModel) -> Unit,
    private val onCancel: () -> Unit,
) : CardActivation {

    override fun onActivate(data: ActivationDataModel) {
        onActivate.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }

}