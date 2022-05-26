package ru.shafran.network.auth

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.account.data.Account
import ru.shafran.network.account.data.GetAttachedAccountRequest
import ru.shafran.network.accounts.AccountsRepository
import ru.shafran.network.companies.CompaniesListStore
import ru.shafran.network.companies.data.Company
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

class ApplicationAuthStoreImpl(
    storeFactory: StoreFactory,
    authenticationStorage: AuthenticationStorage,
    accountsRepository: AccountsRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : ApplicationAuthStore,
    Store<ApplicationAuthStore.Intent, ApplicationAuthStore.State, ApplicationAuthStore.Label> by storeFactory.create(
        name = "ApplicationAuthStore",
        initialState = ApplicationAuthStore.State.Empty,
        executorFactory = {
            Executor(
                authenticationStorage = authenticationStorage,
                accountsRepository = accountsRepository,
                coroutineDispatcher = coroutineDispatcher
            )
        },
        reducer = ReducerImpl,
    ) {

    private object ReducerImpl : Reducer<ApplicationAuthStore.State, Message> {
        override fun ApplicationAuthStore.State.reduce(msg: Message): ApplicationAuthStore.State {
            return when (msg) {
                is Message.Application -> ApplicationAuthStore.State.Application(msg.company)
                is Message.AuthAccount -> ApplicationAuthStore.State.AuthAccount()
                is Message.CompanySelector -> ApplicationAuthStore.State.CompanySelector(msg.account)
                is Message.Error -> ApplicationAuthStore.State.AuthAccount()
            }
        }

        fun Message.Error.reduce(): CompaniesListStore.State {
            return when (exception) {
                else -> {
                    Napier.e({ "Unknown error" }, exception)
                    CompaniesListStore.State.Error.Unknown()
                }
            }
        }

    }

    private class Executor(
        private val authenticationStorage: AuthenticationStorage,
        private val accountsRepository: AccountsRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<ApplicationAuthStore.Intent, Nothing, ApplicationAuthStore.State, Message, ApplicationAuthStore.Label>(
            coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: ApplicationAuthStore.Intent,
            getState: () -> ApplicationAuthStore.State,
        ) {
            when (intent) {
                is ApplicationAuthStore.Intent.LoadAccountAndCompany ->
                    intent.execute()
                is ApplicationAuthStore.Intent.LoadApplication ->
                    intent.execute()
                is ApplicationAuthStore.Intent.LoadCompanySelector ->
                    intent.execute()
            }
        }

        private suspend fun ApplicationAuthStore.Intent.LoadAccountAndCompany.execute() {
            val token = authenticationStorage.getAuthorizedToken()
            if (token == null) {
                syncDispatch(Message.AuthAccount())
                return
            }
            val response = accountsRepository.getAccount(GetAttachedAccountRequest())
                syncDispatch(Message.CompanySelector(response.account))
        }

        private suspend fun ApplicationAuthStore.Intent.LoadApplication.execute() {
            syncDispatch(Message.Application(company))
        }

        private suspend fun ApplicationAuthStore.Intent.LoadCompanySelector.execute() {
            syncDispatch(Message.CompanySelector(account))
        }

    }

    private sealed class Message {

        class AuthAccount() : Message()

        data class CompanySelector(
            val account: Account,
        ) : Message()

        data class Application(
            val company: Company,
        ) : Message()

        class Error(val exception: Exception) : Message()

    }


}