package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.data.card.*
import ru.shafran.cards.repository.CardsRepository
import ru.shafran.cards.usecase.*
import ru.shafran.cards.utils.get
import ru.sulgik.common.ObservableUseCase

class CardInfoComponent(componentContext: ComponentContext) : CardInfo,
    ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { ViewModel(get()) }

    override val currentCard: StateFlow<Result<Card>?> = viewModel.cardByToken

    override fun onShowCard(card: DetectedCard) {
        viewModel.getCardByToken(card.rawToken)
    }


    override fun useCardById(id: Int, data: UsageData) {
        viewModel.useCardById(id, data)
    }

    override fun activateCardById(id: Int, data: ActivationData) {
        viewModel.activateCardById(id, data)
    }

    override fun deactivateCardById(id: Int, data: DeactivationData) {
        viewModel.deactivateCardById(id, data)
    }

    class ViewModel(cardsRepository: CardsRepository) : InstanceKeeper.Instance {

        private val job = SupervisorJob()
        private val scope = CoroutineScope(Dispatchers.Main + job)

        private val getCardByTokenCase =
            ObservableUseCase(GetCardsByTokenUseCase(cardsRepository), scope)

        private val useCardByIdCase = ObservableUseCase(UseCardByIdUseCase(cardsRepository), scope)
        private val activateCardByIdCase =
            ObservableUseCase(ActivateCardByIdUseCase(cardsRepository), scope)
        private val deactivateCardByIdCase =
            ObservableUseCase(DeactivateCardByIdUseCase(cardsRepository), scope)

        val cardByToken = getCardByTokenCase.results

        fun getCardByToken(token: String) = getCardByTokenCase.execute(token)

        fun useCardById(id: Int, data: UsageData) =
            useCardByIdCase.execute(UseCardByIdParam(id, data))

        fun activateCardById(id: Int, data: ActivationData) =
            activateCardByIdCase.execute(ActivateCardByIdParam(id, data))

        fun deactivateCardById(id: Int, data: DeactivationData) =
            deactivateCardByIdCase.execute(DeactivateCardByIdParam(id, data))


        override fun onDestroy() {
            job.cancel()
        }

    }

}