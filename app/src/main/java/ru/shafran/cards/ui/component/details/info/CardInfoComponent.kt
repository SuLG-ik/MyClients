package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.instancekeeper.InstanceKeeper
import com.arkivanov.decompose.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import ru.shafran.cards.GetCardsByTokenUseCase
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.repository.CardsRepository
import ru.shafran.cards.utils.get
import ru.sulgik.common.ObservableUseCase

class CardInfoComponent(componentContext: ComponentContext) : CardInfo,
    ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { ViewModel(get()) }

    override val currentCard: StateFlow<Result<Card>?> = viewModel.cardByToken

    override fun onShowCard(card: DetectedCard) {
        viewModel.getCardByToken(card.rawToken)
    }

    class ViewModel(cardsRepository: CardsRepository) : InstanceKeeper.Instance {

        private val job = SupervisorJob()
        private val scope = CoroutineScope(Dispatchers.Main + job)

        private val getCardByTokenCase =
            ObservableUseCase(GetCardsByTokenUseCase(cardsRepository), scope)

        val cardByToken = getCardByTokenCase.results
        fun getCardByToken(token: String) = getCardByTokenCase.execute(token)

        override fun onDestroy() {
            job.cancel()
        }

    }

}