package ru.shafran.cards.ui.component.details.use

import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.UsageData

class CardUsageComponent(
    card: Card,
    private val onUse: (UsageData) -> Unit,
    private val onCancel: () -> Unit,
) :
    CardUsage {

    override val card: MutableStateFlow<Card> = MutableStateFlow(card)

    override fun onUse(data: UsageData) {
        onUse.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }

}