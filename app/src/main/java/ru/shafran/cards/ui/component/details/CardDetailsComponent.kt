package ru.shafran.cards.ui.component.details

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.shafran.cards.R
import ru.shafran.cards.data.card.*
import ru.shafran.cards.ui.component.details.activation.CardActivationComponent
import ru.shafran.cards.ui.component.details.deactivate.CardDeactivationComponent
import ru.shafran.cards.ui.component.details.error.CardErrorComponent
import ru.shafran.cards.ui.component.details.info.CardInfoComponent
import ru.shafran.cards.ui.component.details.loading.LoadingComponent
import ru.shafran.cards.ui.component.details.use.CardUsageComponent
import ru.shafran.cards.utils.cancelInLifecycle
import ru.shafran.cards.utils.get
import ru.shafran.cards.utils.stores
import ru.shafran.network.card.CardsStore
import ru.shafran.network.data.excpetion.CardCantBeUsedException
import ru.shafran.network.data.excpetion.NotFoundException
import ru.shafran.network.data.excpetion.ServerDoesNotResponseException

class CardDetailsComponent(componentContext: ComponentContext) :
    CardDetails, ComponentContext by componentContext {

    val scope = CoroutineScope(Dispatchers.Main).cancelInLifecycle(lifecycle)

    val viewModel by instanceKeeper.stores { CardsStore(get(), get()) }

    init {
        viewModel.states.onEach {
            when (it) {
                CardsStore.State.CardDoesNotExists -> onCardLoadingFailure(NotFoundException())
                is CardsStore.State.CardLoaded -> onNavigateCard(card = it.card.toModel())
                is CardsStore.State.Error -> {
                    onCardLoadingFailure(it.exception)
                }
                is CardsStore.State.Loading -> router.replaceCurrent(CardDetailsConfiguration.Loading("Загрузка"))
                CardsStore.State.Hidden -> {}
            }
        }.launchIn(scope)
    }

    override val isShown: StateFlow<Boolean> =
        viewModel.states.map { it !is CardsStore.State.Hidden }
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
            else ->
                CardDetailsConfiguration.Error(
                    "Неизвествная ошибка",
                    R.drawable.card_loading,
                )
        }
        router.push(configuration)
    }

    private fun onNavigateCard(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Info(card))
    }

    override fun onShow(cardToken: String) {
        viewModel.accept(CardsStore.Intent.LoadCard(cardToken))
    }

    override fun onHide() {
        viewModel.accept(CardsStore.Intent.Hide)
    }

    override fun onUse(card: CardModel) {
        router.replaceCurrent(CardDetailsConfiguration.Usage(card))
    }

    private fun onUse(card: CardModel, data: UsageDataModel) {
        val description = card.description
        if (description is CardDescriptionModel.Activated) {
            val history = card.history.copy(
                size = card.history.size + 1,
                actions = card.history.actions + CardActionModel.Usage(
                    activationId = description.activation.id, id = -1, data = data,
                )
            )
            router.replaceCurrent(CardDetailsConfiguration.Info(card.copy(history = history)))
            viewModel.accept(CardsStore.Intent.UseCard(card.id, data.toData()))
        } else {
            onHide()
        }
    }


    private fun onActivate(card: CardModel, data: ActivationDataModel) {
        val action = CardActionModel.Activation(data = data, id = -1)
        val history = card.history.copy(
            size = card.history.size + 1,
            actions = card.history.actions + action
        )
        router.replaceCurrent(CardDetailsConfiguration.Info(card.copy(history = history)))
        viewModel.accept(CardsStore.Intent.ActivateCard(card.id, data.toData()))
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
            viewModel.accept(CardsStore.Intent.DeactivationCard(card.id, data.toData()))
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
                        card = configuration.card,
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