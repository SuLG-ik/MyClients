package ru.shafran.cards.ui.component.cardsdetails.info

import com.arkivanov.decompose.ComponentContext
import ru.shafran.cards.data.card.CardModel

class CardInfoComponent(
    componentContext: ComponentContext,
    override val currentCard: CardModel,
    private val onUse: (CardModel) -> Unit,
    private val onActivate: (CardModel) -> Unit,
    private val onDeactivate: (CardModel) -> Unit,
) : CardInfo, ComponentContext by componentContext {


    override fun onUse() {
        onUse(currentCard)
    }

    override fun onActivate() {
        onActivate(currentCard)
    }

    override fun onDeactivate() {
        onDeactivate(currentCard)
    }


}