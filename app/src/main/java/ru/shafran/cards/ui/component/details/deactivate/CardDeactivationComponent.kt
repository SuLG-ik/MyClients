package ru.shafran.cards.ui.component.details.deactivate

import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.DeactivationDataModel

class CardDeactivationComponent(
    card: CardModel,
    private val onDeactivate: (DeactivationDataModel) -> Unit,
    private val onCancel: () -> Unit,
) :
    CardDeactivation {

    override val card: MutableStateFlow<CardModel> = MutableStateFlow(card)

    override fun onDeactivate(data: DeactivationDataModel) {
        onDeactivate.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }
}