package ru.shafran.network.auth

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.AccountIllegalCredentials
import ru.shafran.network.account.data.Account
import ru.shafran.network.account.data.GetAttachedAccountRequest
import ru.shafran.network.accounts.AccountsRepository
import ru.shafran.network.auth.data.LoginAccountRequest
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

class AuthLoginStoreImpl(
    storeFactory: StoreFactory,
    authenticationStorage: AuthenticationStorage,
    authenticationRepository: AuthenticationRepository,
    accountsRepository: AccountsRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : AuthLoginStore,
    Store<AuthLoginStore.Intent, AuthLoginStore.State, AuthLoginStore.Label> by storeFactory.create(
        name = "AuthLoginStore",
        initialState = AuthLoginStore.State.Empty,
        executorFactory = {
            Executor(
                authenticationStorage = authenticationStorage,
                authenticationRepository = authenticationRepository,
                accountsRepository = accountsRepository,
                coroutineDispatcher = coroutineDispatcher
            )
        },
        reducer = ReducerImpl,
    ) {

    private object ReducerImpl : Reducer<AuthLoginStore.State, Message> {
        override fun AuthLoginStore.State.reduce(msg: Message): AuthLoginStore.State {
            return when (msg) {
                is Message.AccountLoginCompleted -> AuthLoginStore.State.AccountLoginCompleted(msg.account)
                is Message.AccountLoginLoading -> AuthLoginStore.State.AccountLoginLoading(msg.request)
                is Message.AccountLoginRequest -> AuthLoginStore.State.AccountLoginRequest()
                is Message.Error -> msg.reduce()
            }
        }

        fun Message.Error.reduce(): AuthLoginStore.State {
            return when (exception) {
                is AccountIllegalCredentials ->
                    AuthLoginStore.State.Error.IllegalCredentials
                else -> {
                    Napier.e({ "Unknown error" }, exception)
                    AuthLoginStore.State.Error.Unknown
                }
            }
        }

    }

    private class Executor(
        private val authenticationStorage: AuthenticationStorage,
        private val authenticationRepository: AuthenticationRepository,
        private val accountsRepository: AccountsRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<AuthLoginStore.Intent, Nothing, AuthLoginStore.State, Message, AuthLoginStore.Label>(
            coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: AuthLoginStore.Intent,
            getState: () -> AuthLoginStore.State,
        ) {
            when (intent) {
                is AuthLoginStore.Intent.Login -> intent.execute()
                is AuthLoginStore.Intent.PrefetchLogin -> syncDispatch(Message.AccountLoginRequest())
            }
        }

        private suspend fun AuthLoginStore.Intent.Login.execute() {
            syncDispatch(Message.AccountLoginLoading(request))
            val response = authenticationRepository.login(request)
            authenticationStorage.setAuthorizedToken(response.token)
            val account = accountsRepository.getAccount(GetAttachedAccountRequest()).account
            syncDispatch(Message.AccountLoginCompleted(account))
        }

    }

    private sealed class Message {

        class AccountLoginRequest : Message()

        data class AccountLoginLoading(
            val request: LoginAccountRequest,
        ) : Message()

        data class AccountLoginCompleted(
            val account: Account,
        ) : Message()

        data class Error(val exception: Exception) : Message()

    }


}