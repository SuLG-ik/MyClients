package ru.shafran.common.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.shafran.common.auth.completed.AuthCompletedComponent
import ru.shafran.common.auth.login.AuthLoginComponent
import ru.shafran.common.auth.type.AuthTypeComponent
import ru.shafran.common.components.R
import ru.shafran.common.error.ErrorComponent
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.createCoroutineScope
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.account.data.Account
import ru.shafran.network.auth.AuthLoginStore
import ru.shafran.network.auth.data.LoginAccountRequest
import ru.shafran.network.utils.reduceStates

class AccountAuthComponent(
    componentContext: ComponentContext,
    private val onContinue: (Account) -> Unit,
) : AccountAuth, ComponentContext by componentContext {

    private val scope = createCoroutineScope()

    private fun reduceState(state: AuthLoginStore.State) {
        when (state) {
            is AuthLoginStore.State.AccountLoginCompleted ->
                router.replaceAll(AccountAuth.Configuration.LoadingCompleted(state.account))
            is AuthLoginStore.State.AccountLoginLoading ->
                router.replaceAll(AccountAuth.Configuration.Loading)
            is AuthLoginStore.State.Empty ->
                router.replaceAll(AccountAuth.Configuration.AuthType)
            is AuthLoginStore.State.AccountLoginRequest ->
                router.replaceAll(AccountAuth.Configuration.LoginRequest)
            is AuthLoginStore.State.Error.Unknown ->
                router.bringToFront(AccountAuth.Configuration.UnknownError)
            AuthLoginStore.State.Error.IllegalCredentials ->
                router.bringToFront(AccountAuth.Configuration.IllegalCredentials)
        }
    }

    val store = getStore<AuthLoginStore>()
        .reduceStates(this, this::reduceState)

    override val isLoading: StateFlow<Boolean> =
        store.states.map { it is AuthLoginStore.State.AccountLoginLoading }
            .stateIn(scope, SharingStarted.Eagerly, false)

    private val onLogin: (LoginAccountRequest) -> Unit = {
        store.accept(AuthLoginStore.Intent.Login(it))
    }


    private val onPrefetchLoginUsernameAndPasswordLogin: () -> Unit = {
        store.accept(AuthLoginStore.Intent.PrefetchLogin())
    }

    private fun createChild(
        configuration: AccountAuth.Configuration,
        componentContext: ComponentContext,
    ): AccountAuth.Child {
        return when (configuration) {
            is AccountAuth.Configuration.AuthType ->
                AccountAuth.Child.AuthType(
                    AuthTypeComponent(
                        onLoginUsernameAndPasswordLogin = onPrefetchLoginUsernameAndPasswordLogin,
                    )
                )
            is AccountAuth.Configuration.Loading ->
                AccountAuth.Child.Loading(
                    LoadingComponent(
                        R.string.accounts_auth_login_loading
                    )
                )
            is AccountAuth.Configuration.LoadingCompleted ->
                AccountAuth.Child.LoadingCompleted(
                    AuthCompletedComponent(
                        account = configuration.account,
                        onContinue = onContinue,
                    )
                )
            is AccountAuth.Configuration.LoginRequest ->
                AccountAuth.Child.LoginRequest(
                    AuthLoginComponent(
                        onLogin = onLogin,
                    )
                )
            AccountAuth.Configuration.IllegalCredentials ->
                AccountAuth.Child.Error(
                    ErrorComponent(
                        R.string.accounts_auth_login_illegal_credentials,
                        R.drawable.warn,
                        onContinue = onPrefetchLoginUsernameAndPasswordLogin,
                    )
                )
            AccountAuth.Configuration.UnknownError ->
                AccountAuth.Child.Error(
                    ErrorComponent(
                        R.string.unknwon_error,
                        R.drawable.error,
                        onContinue = onPrefetchLoginUsernameAndPasswordLogin,
                    )
                )
        }
    }

    val router = router<AccountAuth.Configuration, AccountAuth.Child>(
        initialConfiguration = AccountAuth.Configuration.AuthType,
        childFactory = this::createChild
    )

    override val routerState: Value<RouterState<AccountAuth.Configuration, AccountAuth.Child>>
        get() = router.state

}