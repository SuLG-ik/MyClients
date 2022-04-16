package ru.shafran.common.sessions.stats

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.session.SessionsStatsStore
import ru.shafran.network.session.data.GetSessionsStatsRequest
import ru.shafran.network.utils.DatePeriod
import ru.shafran.network.utils.reduceStates
import java.time.LocalDate

class AllSessionsStatsHostComponent(
    componentContext: ComponentContext,
) : AllSessionsStatsHost, ComponentContext by componentContext {

    val store = getStore<SessionsStatsStore>()
        .reduceStates(this, this::reduceState)


    private fun reduceState(state: SessionsStatsStore.State) {
        when (state) {
            is SessionsStatsStore.State.Empty -> onUpdate(null)
            is SessionsStatsStore.State.Error ->
                router.replaceAll(AllSessionsStatsHost.Configuration.UnknownError)
            is SessionsStatsStore.State.Loading ->
                router.replaceAll(AllSessionsStatsHost.Configuration.Loading)
            is SessionsStatsStore.State.StatsLoaded ->
                router.replaceAll(AllSessionsStatsHost.Configuration.StatsLoaded(
                    period = state.period,
                    stats = state.stats,
                ))
        }
    }

    private val onUpdate: (DatePeriod?) -> Unit = { period ->
        store.accept(
            SessionsStatsStore.Intent.LoadStats(
                GetSessionsStatsRequest(
                    period ?: DatePeriod(
                        from = LocalDate.now().minusMonths(1),
                        to = LocalDate.now()
                    ))
            )
        )
    }

    val router = router<AllSessionsStatsHost.Configuration, AllSessionsStatsHost.Child>(
        initialConfiguration = AllSessionsStatsHost.Configuration.Loading,
        childFactory = this::createChild,
    )

    private fun createChild(
        configuration: AllSessionsStatsHost.Configuration,
        componentContext: ComponentContext,
    ): AllSessionsStatsHost.Child {
        return when (configuration) {
            is AllSessionsStatsHost.Configuration.Loading ->
                AllSessionsStatsHost.Child.Loading(
                    LoadingComponent(
                        R.string.sessions_stats_loading,
                    )
                )
            is AllSessionsStatsHost.Configuration.StatsLoaded ->
                AllSessionsStatsHost.Child.StatsLoaded(
                    AllSessionsStatsComponent(
                        period = configuration.period,
                        stats = configuration.stats,
                        onChangePeriod = onUpdate,
                    )
                )
            is AllSessionsStatsHost.Configuration.UnknownError ->
                AllSessionsStatsHost.Child.Error(
                    ErrorComponent(
                        R.string.unknwon_error,
                        R.drawable.error,
                        onContinue = { onUpdate(null) },
                    )
                )
        }
    }

    override val routerState: Value<RouterState<AllSessionsStatsHost.Configuration, AllSessionsStatsHost.Child>>
        get() = router.state

}