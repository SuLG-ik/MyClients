package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.GetCustomerByIdRequest
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

internal class CustomerEditingStoreImpl(
    private val factory: StoreFactory,
    customersRepository: CustomersRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : CustomerEditingStore,
    Store<CustomerEditingStore.Intent, CustomerEditingStore.State, CustomerEditingStore.Label> by factory.create(
        name = "CustomerEditingStore",
        initialState = CustomerEditingStore.State.Empty,
        executorFactory = { Executor(customersRepository, coroutineDispatcher) },
        reducer = ReducerImpl
    ) {


    private object ReducerImpl : Reducer<CustomerEditingStore.State, Message> {
        override fun CustomerEditingStore.State.reduce(msg: Message): CustomerEditingStore.State {
            return when (msg) {
                is Message.DetailsLoaded -> CustomerEditingStore.State.DetailsLoaded(msg.customer)
                is Message.Empty -> CustomerEditingStore.State.Empty
                is Message.Error -> TODO()
                is Message.Loading -> CustomerEditingStore.State.Loading()
            }
        }
    }

    private class Executor(
        private val customersRepository: CustomersRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        CancelableSyncCoroutineExecutor<CustomerEditingStore.Intent, Nothing, CustomerEditingStore.State, Message, CustomerEditingStore.Label>(
            coroutineDispatcher) {
        override suspend fun execute(
            intent: CustomerEditingStore.Intent,
            getState: () -> CustomerEditingStore.State,
        ) {
            when (intent) {
                is CustomerEditingStore.Intent.Edit ->
                    intent.execute()
                is CustomerEditingStore.Intent.LoadDetails ->
                    intent.execute()
            }
        }

        private suspend fun CustomerEditingStore.Intent.LoadDetails.execute() {
            syncDispatch(Message.Loading())
            val customer = customersRepository.getCustomerById(GetCustomerByIdRequest(customerId))
            syncDispatch(Message.DetailsLoaded(customer.customer))
        }


        private suspend fun CustomerEditingStore.Intent.Edit.execute() {
            syncDispatch(Message.Loading())
            customersRepository.editCustomerData(request)
            syncPublish(CustomerEditingStore.Label.EditCompleted)
        }
    }

    private sealed class Message {
        object Empty : Message()

        data class DetailsLoaded(
            val customer: Customer,
        ) : Message()

        class Loading() : Message()

        data class Error(val exception: Exception) : Message()
    }


}

