package ru.shafran.network.companies

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.companies.data.Company
import ru.shafran.network.companies.data.GetAvailableCompaniesListRequest
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

class CompaniesListStoreImpl(
    storeFactory: StoreFactory,
    companiesRepository: CompaniesRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : CompaniesListStore,
    Store<CompaniesListStore.Intent, CompaniesListStore.State, CompaniesListStore.Label> by storeFactory.create(
        name = "CompaniesListStore",
        initialState = CompaniesListStore.State.Empty,
        executorFactory = { Executor(companiesRepository, coroutineDispatcher) },
        reducer = ReducerImpl,
    ) {

    private object ReducerImpl : Reducer<CompaniesListStore.State, Message> {
        override fun CompaniesListStore.State.reduce(msg: Message): CompaniesListStore.State {
            return when (msg) {
                is Message.CompaniesList ->
                    CompaniesListStore.State.CompaniesList(msg.companies)
                is Message.CompaniesListLoading ->
                    CompaniesListStore.State.CompaniesListLoading
                is Message.Error -> msg.reduce()
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
        private val companiesRepository: CompaniesRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<CompaniesListStore.Intent, Nothing, CompaniesListStore.State, Message, CompaniesListStore.Label>(
            coroutineDispatcher) {

        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: CompaniesListStore.Intent,
            getState: () -> CompaniesListStore.State,
        ) {
            when (intent) {
                is CompaniesListStore.Intent.LoadCompaniesList ->
                    intent.execute()
            }
        }

        private suspend fun CompaniesListStore.Intent.LoadCompaniesList.execute() {
            syncDispatch(Message.CompaniesListLoading)
            val request = GetAvailableCompaniesListRequest(
                accountId = accountId,
                offset = offset,
                page = page
            )
            val response = companiesRepository.getAvailableCompaniesList(request)
            syncDispatch(Message.CompaniesList(response.companies))
        }


    }

    private sealed class Message {
        object CompaniesListLoading : Message()

        class CompaniesList(
            val companies: List<Company>,
        ) : Message()

        class Error(val exception: Exception) : Message()

    }


}