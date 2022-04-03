package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.customers.data.FoundCustomerItem
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

internal class CustomerSearchStoreImpl(
    private val factory: StoreFactory,
    customersRepository: CustomersRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : CustomerSearchStore,
    Store<CustomerSearchStore.Intent, CustomerSearchStore.State, CustomerSearchStore.Label> by factory.create(
        name = "CustomerSearchStore",
        initialState = CustomerSearchStore.State.Empty,
        executorFactory = { Executor(customersRepository, coroutineDispatcher) },
        reducer = ReducerImpl
    ) {


    private object ReducerImpl : Reducer<CustomerSearchStore.State, Message> {
        override fun CustomerSearchStore.State.reduce(msg: Message): CustomerSearchStore.State {
            return when (msg) {
                is Message.SearchCompleted -> CustomerSearchStore.State.SearchCompleted(msg.searchResult)
                is Message.Empty -> CustomerSearchStore.State.Empty
                is Message.Error -> msg.reduce()
                is Message.Loading -> CustomerSearchStore.State.Loading()
            }
        }

        private fun Message.Error.reduce(): CustomerSearchStore.State {
            return when (exception) {
                else -> {
                    Napier.e({ "Unknown exception" }, exception)
                    CustomerSearchStore.State.Error.Unknown
                }
            }
        }


    }

    private class Executor(
        private val customersRepository: CustomersRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<CustomerSearchStore.Intent, Nothing, CustomerSearchStore.State, Message, CustomerSearchStore.Label>(
            coroutineDispatcher) {
        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: CustomerSearchStore.Intent,
            getState: () -> CustomerSearchStore.State,
        ) {
            when (intent) {
                is CustomerSearchStore.Intent.SearchCustomerByPhone ->
                    intent.execute()
            }
        }

        private suspend fun CustomerSearchStore.Intent.SearchCustomerByPhone.execute() {
            syncDispatch(Message.Loading())
            val customer = customersRepository.searchCustomerByPhone(request)
            syncDispatch(Message.SearchCompleted(customer.searchResult))
        }

    }

    private sealed class Message {
        object Empty : Message()

        data class SearchCompleted(
            val searchResult: List<FoundCustomerItem>,
        ) : Message()

        class Loading() : Message()

        data class Error(val exception: Exception) : Message()
    }


}

