package ru.shafran.cards.ui.component.details

import android.util.Log
import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import ru.shafran.cards.R
import ru.shafran.cards.data.card.*
import ru.shafran.cards.data.excpetion.CardCantBeUsedException
import ru.shafran.cards.data.excpetion.NotFoundException
import ru.shafran.cards.data.excpetion.ServerDoesNotResponseException
import ru.shafran.cards.ui.component.details.activation.CardActivationComponent
import ru.shafran.cards.ui.component.details.deactivate.CardDeactivationComponent
import ru.shafran.cards.ui.component.details.error.CardErrorComponent
import ru.shafran.cards.ui.component.details.info.CardInfoComponent
import ru.shafran.cards.ui.component.details.loading.LoadingComponent
import ru.shafran.cards.ui.component.details.use.CardUsageComponent
import ru.shafran.cards.usecase.UseCaseFactory
import ru.shafran.cards.usecase.cards.param.ActivateCardByIdParam
import ru.shafran.cards.usecase.cards.param.DeactivateCardByIdParam
import ru.shafran.cards.usecase.cards.param.UseCardByIdParam
import ru.shafran.cards.utils.get
import ru.sulgik.common.asObservable

class CardDetailsComponent(componentContext: ComponentContext) :
    CardDetails, ComponentContext by componentContext,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val viewModel = instanceKeeper.getOrCreate { ViewModel(get()) }

    init {
        lifecycle.doOnDestroy(this::cancel)
        viewModel.cardByToken.drop(1).onEach { result ->
            if (result == null) {
                onCardLoading()
            } else {
                result.onSuccess {
                    onShow(it)
                    Log.d("fsdgfadsfds", "pososi")
                }
                result.onFailure {
                    onCardLoadingFailure(it as Exception)
                    Log.w("fsdgfadsfds", it)
                }
            }
        }.launchIn(this)
    }

    override val isShown: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun onShow(card: Card) {
        router.replaceCurrent(CardDetailsConfiguration.Info(card))
    }

    override fun onShow(card: DetectedCard) {
        viewModel.getCardByToken(card.rawToken)
    }

    private fun onCardLoading() {
        isShown.update { true }
        router.navigate { listOf(CardDetailsConfiguration.Loading("Загрузка карты")) }
    }

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

    override fun onHide() {
        isShown.update { false }
    }

    override fun onUse(card: Card) {
        router.replaceCurrent(CardDetailsConfiguration.Usage(card))
    }

    private fun onUse(card: Card, data: UsageData) {
        val description = card.description
        if (description is CardDescription.Activated) {
            val history = card.history.copy(
                size = card.history.size + 1,
                actions = card.history.actions + CardAction.Usage(
                    activationId = description.activation.id, id = -1, data = data,
                )
            )
            router.replaceCurrent(CardDetailsConfiguration.Info(card.copy(history = history)))
            viewModel.useCardById(card.id, data)
        } else {
            onHide()
        }
    }


    private fun onActivate(card: Card, data: ActivationData) {
        val action = CardAction.Activation(data = data, id = -1)
        val history = card.history.copy(size = card.history.size + 1,
            actions = card.history.actions + action)
        router.replaceCurrent(CardDetailsConfiguration.Info(card.copy(history = history)))
        viewModel.activateCardById(card.id, data)
    }

    override fun onActivate(card: Card) {
        router.replaceCurrent(CardDetailsConfiguration.Activation(card))
    }

    private fun onDeactivate(card: Card, data: DeactivationData) {
        val description = card.description
        if (description is CardDescription.Activated) {
            val action = CardAction.Deactivation(
                data = data,
                id = -1,
                activationId = description.activation.id
            )
            val history = card.history.copy(size = card.history.size + 1,
                actions = card.history.actions + action)
            router.replaceCurrent(CardDetailsConfiguration.Info(card.copy(history = history)))
            viewModel.deactivateCardById(card.id, data)
        } else {
            onHide()
        }

    }

    override fun onDeactivate(card: Card) {
        router.replaceCurrent(CardDetailsConfiguration.Deactivation(card))
    }

    private val router: Router<CardDetailsConfiguration, CardDetails.Child> =
        router(initialConfiguration = CardDetailsConfiguration.Loading("Загрузка карт"),
            childFactory = this::createChild)
    override val state: Value<RouterState<CardDetailsConfiguration, CardDetails.Child>>
        get() = router.state

    private fun createChild(
        configuration: CardDetailsConfiguration,
        componentContext: ComponentContext,
    ): CardDetails.Child {
        return when (configuration) {
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
                        onCancel = { onShow(configuration.card) },
                    )
                )
            is CardDetailsConfiguration.Deactivation ->
                CardDetails.Child.Deactivation(
                    CardDeactivationComponent(
                        card = configuration.card,
                        onDeactivate = { onDeactivate(configuration.card, it) },
                        onCancel = { onShow(configuration.card) },
                    )
                )
            is CardDetailsConfiguration.Usage ->
                CardDetails.Child.Usage(
                    CardUsageComponent(
                        card = configuration.card,
                        onUse = { onUse(configuration.card, it) },
                        onCancel = { onShow(configuration.card) },
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

    class ViewModel(
        factory: UseCaseFactory,
    ) : InstanceKeeper.Instance {

        private val job = SupervisorJob()
        private val scope = CoroutineScope(Dispatchers.Main + job)

        private val getCardByTokenCase = factory.cardByToken().asObservable(scope, Dispatchers.IO)

        private val useCardByIdCase =
            factory.useCard().asObservable(scope, Dispatchers.IO)
        private val activateCardByIdCase =
            factory.activateCard().asObservable(scope, Dispatchers.IO)
        private val deactivateCardByIdCase =
            factory.deactivateCard().asObservable(scope, Dispatchers.IO)

        val cardByToken = getCardByTokenCase.results

        fun getCardByToken(token: String) = getCardByTokenCase.execute(token)

        fun useCardById(id: Long, data: UsageData) =
            useCardByIdCase.execute(UseCardByIdParam(id, data))

        fun activateCardById(id: Long, data: ActivationData) =
            activateCardByIdCase.execute(ActivateCardByIdParam(id, data))

        fun deactivateCardById(id: Long, data: DeactivationData) =
            deactivateCardByIdCase.execute(DeactivateCardByIdParam(id, data))


        override fun onDestroy() {
            job.cancel()
        }

    }

}