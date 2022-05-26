package ru.shafran.common.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.common.auth.AccountAuthComponent
import ru.shafran.common.companies.CompanySelectorComponent
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.root.CompanyTargetApplicationComponent
import ru.shafran.common.utils.SettingsNavigator
import ru.shafran.common.utils.Share
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.getStore
import ru.shafran.network.account.data.Account
import ru.shafran.network.auth.ApplicationAuthStore
import ru.shafran.network.companies.data.Company
import ru.shafran.network.utils.reduceStates

class MainComponent(
    componentContext: ComponentContext,
    private val share: Share,
    private val settingsNavigator: SettingsNavigator,
) : Main, Updatable, ComponentContext by componentContext {


    val store = getStore<ApplicationAuthStore>()
        .reduceStates(this, this::reduceState)

    private fun reduceState(state: ApplicationAuthStore.State) {
        when (state) {
            is ApplicationAuthStore.State.Empty ->
                onUpdate()
            is ApplicationAuthStore.State.Application ->
                router.bringToFront(Main.Configuration.CompanyTargetApplication(state.company))
            is ApplicationAuthStore.State.AuthAccount ->
                router.bringToFront(Main.Configuration.Auth)
            is ApplicationAuthStore.State.CompanySelector ->
                router.bringToFront(Main.Configuration.CompanySelector(state.account))
            is ApplicationAuthStore.State.Error.Unknown ->
                router.bringToFront(Main.Configuration.Error)
        }
    }

    override val onUpdate: () -> Unit = {
        store.accept(ApplicationAuthStore.Intent.LoadAccountAndCompany)
    }

    private val onCompanyApplication: (Account, Company) -> Unit = { account, company ->
        store.accept(ApplicationAuthStore.Intent.LoadApplication(account, company))
    }

    private val onSelectCompanies: (Account) -> Unit = {
        store.accept(ApplicationAuthStore.Intent.LoadCompanySelector(it))
    }

    private fun createChild(
        configuration: Main.Configuration,
        componentContext: ComponentContext,
    ): Main.Child {
        return when (configuration) {
            is Main.Configuration.Auth ->
                Main.Child.Auth(
                    AccountAuthComponent(
                        componentContext = componentContext,
                        onContinue = onSelectCompanies,
                    )
                )
            is Main.Configuration.CompanyTargetApplication ->
                Main.Child.Application(
                    CompanyTargetApplicationComponent(
                        componentContext = componentContext,
                        share = share,
                        onOpenSettings = settingsNavigator::openSettings,
                        company = configuration.company,
                    )
                )
            is Main.Configuration.Splash ->
                Main.Child.Splash(SplashComponent())
            is Main.Configuration.CompanySelector ->
                Main.Child.CompanySelector(
                    CompanySelectorComponent(
                        componentContext = componentContext,
                        onSelect = { onCompanyApplication(configuration.account, it) },
                        account = configuration.account,
                    )
                )
            Main.Configuration.Error ->
                Main.Child.Error(
                    ErrorComponent(
                        message = R.string.unknwon_error,
                        icon = R.drawable.error,
                        onContinue = onUpdate,
                    )
                )
        }
    }

    private val router = router<Main.Configuration, Main.Child>(
        initialConfiguration = Main.Configuration.Splash,
        key = "main_router",
        childFactory = this::createChild,
    )

    override val isLoading: Value<Boolean>
        get() = routerState.map { it.activeChild.instance is Main.Child.Splash }
    override val routerState: Value<RouterState<Main.Configuration, Main.Child>>
        get() = router.state

}