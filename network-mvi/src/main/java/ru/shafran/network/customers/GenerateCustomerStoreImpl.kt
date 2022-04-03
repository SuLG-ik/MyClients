package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.utils.SafeCancelableSyncCoroutineExecutor

class GenerateCustomerStoreImpl(
    private val factory: StoreFactory,
    private val customersRepository: CustomersRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : GenerateCustomerStore,
    Store<GenerateCustomerStore.Intent, GenerateCustomerStore.State, GenerateCustomerStore.Label> by factory.create(
        name = "GenerateCustomerStore",
        initialState = GenerateCustomerStore.State.Request(),
        executorFactory = {
            Executor(
                customersRepository = customersRepository,
                coroutineDispatcher = coroutineDispatcher,
            )
        },
        reducer = ReducerImpl
    ) {

    private object ReducerImpl :
        Reducer<GenerateCustomerStore.State, Message> {
        override fun GenerateCustomerStore.State.reduce(msg: Message): GenerateCustomerStore.State {
            return when (msg) {
                is Message.CustomerGenerated ->
                    GenerateCustomerStore.State.CustomerGenerated(msg.token, msg.customer)
                is Message.Error -> msg.reduce()
                is Message.Loading ->
                    GenerateCustomerStore.State.Loading()
                is Message.Request ->
                    GenerateCustomerStore.State.Request()
            }
        }

        private fun Message.Error.reduce(): GenerateCustomerStore.State {
            return when (exception) {
                else -> {
                    Napier.e({ "Unknown exception" }, exception)
                    GenerateCustomerStore.State.Error.Unknown
                }
            }
        }

    }

    private class Executor(
        private val customersRepository: CustomersRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        SafeCancelableSyncCoroutineExecutor<GenerateCustomerStore.Intent, Nothing, GenerateCustomerStore.State, Message, GenerateCustomerStore.Label>(
            coroutineDispatcher) {


        override suspend fun buildErrorMessage(exception: Exception): Message {
            return Message.Error(exception)
        }

        override suspend fun safeExecute(
            intent: GenerateCustomerStore.Intent,
            getState: () -> GenerateCustomerStore.State,
        ) {
                when (intent) {
                    is GenerateCustomerStore.Intent.GenerateCustomer ->
                        intent.execute()
                }
        }

        private suspend fun GenerateCustomerStore.Intent.GenerateCustomer.execute() {
            syncDispatch(Message.Loading())
            val response = customersRepository.createCustomer(request)
            syncDispatch(Message.CustomerGenerated(response.token, response.customer))
        }
    }


    private sealed class Message {

        class Request() : Message()

        class Loading() : Message()

        class CustomerGenerated(
            val token: String,
            val customer: Customer.ActivatedCustomer,
        ) : Message()

        class Error(val exception: Exception) : Message()

    }

}