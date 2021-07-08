package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.replaceCurrent
import com.arkivanov.decompose.router
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard

class CardInfoComponent(componentContext: ComponentContext) : CardInfo,
    ComponentContext by componentContext {

    val router =
        router<CardInfoConfig, CardInfo.Child>(
            CardInfoConfig.Loading(""),
            key = "CardInfoRouter",
            childFactory = this::createChild
        )

    override val routerState: Value<RouterState<CardInfoConfig, CardInfo.Child>> = router.state

    private fun createChild(config: CardInfoConfig, context: ComponentContext): CardInfo.Child {
        return when (config) {
            is CardInfoConfig.Loading -> {
                val component =
                    if (config.rawToken.isEmpty()) InfinityLoading() else LoadingComponent(
                        config.rawToken,
                        this::onShowCard
                    )
                CardInfo.Child.Loading(component)
            }
            is CardInfoConfig.Success -> {
                CardInfo.Child.Success(CardInfoLoadedComponent(config.card))
            }
        }
    }

    override fun onShowCard(card: Card) {
        router.replaceCurrent(CardInfoConfig.Success(card))
    }


    override fun onShowCard(card: DetectedCard) {
        router.replaceCurrent(CardInfoConfig.Loading(card.rawToken))
    }

}