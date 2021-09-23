package ru.shafran.cards.ui.component.details.activation

import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.ActivationDataModel
import ru.shafran.cards.data.card.CardModel

class CardActivationComponent(
    card: CardModel,
    private val onActivate: (ActivationDataModel) -> Unit,
    private val onCancel: () -> Unit,
) : CardActivation {

    override val card: MutableStateFlow<CardModel> = MutableStateFlow(card)

    override fun onActivate(data: ActivationDataModel) {
        onActivate.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }

}