package ru.shafran.cards.ui.component.cardsdetails

import android.util.Log
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
import ru.shafran.network.data.excpetion.CardCantBeUsedException
import ru.shafran.network.data.excpetion.NotFoundException
import ru.shafran.network.data.excpetion.ServerDoesNotResponseException
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
                CardsStore.State.CardDoesNotExists -> onCardLoadingFailure(NotFoundException())
                is CardsStore.State.CardLoaded -> onNavigateCard(card = it.card.toModel())
                is CardsStore.State.Error -> {
                    onCardLoadingFailure(it.exception)
                }
                is CardsStore.State.Loading -> router.replaceCurrent(CardDetailsConfiguration.Loading(
                    "Загрузка"))
                CardsStore.State.Hidden -> {
                }
            }
        }.launchIn(scope)
    }

    override val isShown: StateFlow<Boolean> =
        cardsStore.states.map { it !is CardsStore.State.Hidden }
            .stateIn(scope, SharingStarted.Lazily, false)


    private fun onCardLoadingFailure(e: Exception) {
        val configuration = when (e) {
            is CardCantBeUsedException ->
                CardDetailsConfiguration.Error(
                    "Карты не активирована",
                    R.drawable.card_loading,
                )
            is NotFoundException ->
                CardDetailsConfiguration.Error(
                    "Карты не существует",
                    R.drawable.card_loading,
                )
            is ServerDoesNotResponseException ->
                CardDetailsConfiguration.Error(
                    "Сервер не отвечает",
                    R.drawable.card_loading,
                )
            else -> {
                Log.w("CardDetailsError", e)
                CardDetailsConfiguration.Error(
                    "Неизвествная ошибка",
                    R.drawable.card_loading,
                )
            }
        }
        router.push(configuration)
    }

    private fun onNavigateCard(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Info(card))
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

    override fun onUse(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Usage(card))
    }

    private fun onUse(card: CardModel, data: UsageDataModel) {
        cardsStore.accept(CardsStore.Intent.UseCard(card.id, data.toData()))
    }


    private fun onActivate(card: CardModel, data: ActivationDataModel) {
        cardsStore.accept(CardsStore.Intent.ActivateCard(card.id, data.toData()))
    }

    override fun onActivate(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Activation(card))
    }

    private fun onDeactivate(card: CardModel, data: DeactivationDataModel) {
        val description = card.description
        if (description is CardDescriptionModel.Activated) {
            val action = CardActionModel.Deactivation(
                data = data,
                id = -1,
                activationId = description.activation.id
            )
            val history = card.history.copy(
                size = card.history.size + 1,
                actions = card.history.actions + action
            )
            router.replaceCurrent(CardDetailsConfiguration.Info(card.copy(history = history)))
            cardsStore.accept(CardsStore.Intent.DeactivationCard(card.id, data.toData()))
        } else {
            onHide()
        }

    }

    override fun onDeactivate(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Deactivation(card))
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