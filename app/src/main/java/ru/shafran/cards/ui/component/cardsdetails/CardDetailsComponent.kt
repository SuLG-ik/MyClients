package ru.shafran.cards.ui.component.cardsdetails

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.shafran.cards.R
import ru.shafran.cards.data.card.*
import ru.shafran.cards.ui.component.cardsdetails.activation.CardActivationComponent
import ru.shafran.cards.ui.component.cardsdetails.deactivate.CardDeactivationComponent
import ru.shafran.cards.ui.component.cardsdetails.error.CardErrorComponent
import ru.shafran.cards.ui.component.cardsdetails.info.CardInfoComponent
import ru.shafran.cards.ui.component.cardsdetails.use.CardUsageComponent
import ru.shafran.cards.ui.component.loading.LoadingComponent
import ru.shafran.cards.utils.cancelInLifecycle
import ru.shafran.network.card.CardsStore
import ru.shafran.network.employee.EmployeesListStore

class CardDetailsComponent(
    componentContext: ComponentContext,
    private val cardsStore: CardsStore,
    private val employeesStore: EmployeesListStore,
) : CardDetails, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main).cancelInLifecycle(lifecycle)

    init {
        cardsStore.states.onEach {
            when (it) {
                is CardsStore.State.CardLoaded ->
                    onNavigateCard(card = it.card.toModel())
                is CardsStore.State.Error ->
                    onCardLoadingFailure(it)
                is CardsStore.State.Loading ->
                    router.replaceCurrent(CardDetailsConfiguration.Loading("Загрузка"))
                CardsStore.State.Hidden ->
                    router.replaceCurrent(CardDetailsConfiguration.Hidden)

            }
        }.launchIn(scope)
    }

    override val isShown: StateFlow<Boolean> =
        cardsStore.states.map { it !is CardsStore.State.Hidden }
            .stateIn(scope, SharingStarted.Lazily, false)


    private fun onCardLoadingFailure(error: CardsStore.State.Error) {
        val configuration = when (error) {
            is CardsStore.State.Error.CardMustBeActivated -> {
                CardDetailsConfiguration.Error(
                    "Карты не активирована",
                    R.drawable.card_loading,
                )
            }
            is CardsStore.State.Error.CardDoesNotExists ->
                CardDetailsConfiguration.Error(
                    "Карты не существует",
                    R.drawable.card_loading,
                )
            is CardsStore.State.Error.ConnectionLost ->
                CardDetailsConfiguration.Error(
                    "Соединение потеряно",
                    R.drawable.card_loading,
                )
            CardsStore.State.Error.InternalServerError ->
                CardDetailsConfiguration.Error(
                    "Внутренняя ошибка",
                    R.drawable.card_loading,
                )
            CardsStore.State.Error.UnknownError ->
                CardDetailsConfiguration.Error(
                    "Неизвестная ошибка",
                    R.drawable.card_loading,
                )
        }
        router.push(configuration)
    }

    private fun onNavigateCard(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Info(card))
    }

    override fun onUse(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Usage(card))
    }

    override fun onActivate(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Activation(card))
    }

    override fun onDeactivate(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Deactivation(card))
    }

    override fun onShowByCardToken(cardToken: String) {
        cardsStore.accept(CardsStore.Intent.LoadCardByToken(cardToken))
    }

    override fun onShowByActivationId(activationId: Long) {
        cardsStore.accept(CardsStore.Intent.LoadCardByActivationId(activationId))
    }


    override fun onHide() {
        cardsStore.accept(CardsStore.Intent.Hide)
    }

    private fun onUse(card: CardModel, data: UsageDataModel) {
        cardsStore.accept(CardsStore.Intent.UseCard(card.id, data.toData()))
    }

    private fun onActivate(card: CardModel, data: ActivationDataModel) {
        cardsStore.accept(CardsStore.Intent.ActivateCard(card.id, data.toData()))
    }

    private fun onDeactivate(card: CardModel, data: DeactivationDataModel) {
        cardsStore.accept(CardsStore.Intent.DeactivationCard(card.id, data.toData()))
    }

    private val router: Router<CardDetailsConfiguration, CardDetails.Child> =
        router(
            initialConfiguration = CardDetailsConfiguration.Hidden,
            childFactory = this::createChild
        )
    override val state: Value<RouterState<CardDetailsConfiguration, CardDetails.Child>>
        get() = router.state

    private fun createChild(
        configuration: CardDetailsConfiguration,
        componentContext: ComponentContext,
    ): CardDetails.Child {
        return when (configuration) {
            is CardDetailsConfiguration.Hidden -> CardDetails.Child.Hidden
            is CardDetailsConfiguration.Info ->
                CardDetails.Child.Info(
                    CardInfoComponent(
                        componentContext,
                        currentCard = configuration.card,
                        onUse = { onUse(it) },
                        onActivate = { onActivate(it) },
                        onDeactivate = { onDeactivate(it) },
                    )
                )
            is CardDetailsConfiguration.Loading ->
                CardDetails.Child.Loading(
                    LoadingComponent(configuration.message)
                )
            is CardDetailsConfiguration.Activation ->
                CardDetails.Child.Activation(
                    CardActivationComponent(
                        card = configuration.card,
                        onActivate = { onActivate(configuration.card, it) },
                        onCancel = { onNavigateCard(configuration.card) },
                    )
                )
            is CardDetailsConfiguration.Deactivation ->
                CardDetails.Child.Deactivation(
                    CardDeactivationComponent(
                        card = configuration.card,
                        onDeactivate = { onDeactivate(configuration.card, it) },
                        onCancel = { onNavigateCard(configuration.card) },
                    )
                )
            is CardDetailsConfiguration.Usage ->
                CardDetails.Child.Usage(
                    CardUsageComponent(
                        card = configuration.card,
                        onUse = { onUse(configuration.card, it) },
                        onCancel = { onNavigateCard(configuration.card) },
                        employeesStore = employeesStore,
                    )
                )
            is CardDetailsConfiguration.Error ->
                CardDetails.Child.Error(
                    CardErrorComponent(
                        message = configuration.message,
                        icon = configuration.icon,
                        onReview = {
                            val backStack = router.state.value.backStack
                            val previousScreen = backStack.getOrNull(backStack.size - 2)
                            if (previousScreen != null && previousScreen.configuration !is CardDetailsConfiguration.Loading) {
                                router.pop()
                            } else {
                                onHide()
                            }
                        },
                    )
                )
        }
    }
}