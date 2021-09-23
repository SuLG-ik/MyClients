package ru.shafran.cards.ui.component.details.use

import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.UsageDataModel

class CardUsageComponent(
    card: CardModel,
    private val onUse: (UsageDataModel) -> Unit,
    private val onCancel: () -> Unit,
) :
    CardUsage {

    override val card: MutableStateFlow<CardModel> = MutableStateFlow(card)

    override fun onUse(data: UsageDataModel) {
        onUse.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }

}