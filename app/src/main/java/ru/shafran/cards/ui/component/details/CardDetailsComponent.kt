package ru.shafran.cards.ui.component.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.info.CardInfo
import ru.shafran.cards.ui.component.details.info.CardInfoComponent

class CardDetailsComponent(componentContext: ComponentContext) :
    CardDetails, ComponentContext by componentContext {


    override val info: CardInfo = CardInfoComponent(childContext("card_info_component"))

    override val isShown: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun onShow(card: DetectedCard) {
        isShown.value = true
        info.onShowCard(card)
    }

    override fun onHide() {
        isShown.value = false
    }


}