package ru.shafran.network.customers

import beauty.shafran.network.customers.exceptions.CustomersException
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.GetCustomerByIdRequest
import ru.shafran.network.customers.data.GetCustomerByTokenRequest
import ru.shafran.network.session.SessionsRepository
import ru.shafran.network.session.data.GetSessionsForCustomerRequest
import ru.shafran.network.session.data.Session
import ru.shafran.network.utils.CancelableSyncCoroutineExecutor

internal class CustomerInfoStoreImpl(
    private val factory: StoreFactory,
    private val customersRepository: CustomersRepository,
    private val sessionsRepository: SessionsRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : CustomerInfoStore,
    Store<CustomerInfoStore.Intent, CustomerInfoStore.State, CustomerInfoStore.Label> by factory.create(
        name = "CustomerInfoStore",
        initialState = CustomerInfoStore.State.Empty,
        executorFactory = {
            Executor(
                customersRepository = customersRepository,
                sessionsRepository = sessionsRepository,
                coroutineDispatcher = coroutineDispatcher,
            )
        },
        reducer = ReducerImpl
    ) {


    private class Executor(
        private val customersRepository: CustomersRepository,
        private val sessionsRepository: SessionsRepository,
        coroutineDispatcher: CoroutineDispatcher,
    ) :
        CancelableSyncCoroutineExecutor<CustomerInfoStore.Intent, Nothing, CustomerInfoStore.State, Message, CustomerInfoStore.Label>(
            coroutineDispatcher) {


        override suspend fun execute(
            intent: CustomerInfoStore.Intent,
            getState: () -> CustomerInfoStore.State,
        ) {
            coroutineScope {
                try {
                    when (intent) {
                        is CustomerInfoStore.Intent.LoadCustomerWithToken ->
                            intent.execute()
                        is CustomerInfoStore.Intent.LoadCustomerWithId ->
                            intent.execute()
                    }
                } catch (e: Exception) {
                    syncDispatch(Message.Error(e))
                }
            }
        }

        private suspend fun CustomerInfoStore.Intent.LoadCustomerWithId.execute() {
            val response =
                customersRepository.getCustomerById(GetCustomerByIdRequest(customerId))
            syncDispatch(Message.CustomerPreloaded(response.cardToken, response.customer))
            loadSessions(response.cardToken, response.customer)
        }

        private suspend fun CustomerInfoStore.Intent.LoadCustomerWithToken.execute() {
            val response =
                customersRepository.getCustomerByToken(GetCustomerByTokenRequest(customerToken))
            syncDispatch(Message.CustomerPreloaded(response.cardToken, response.customer))
            loadSessions(cardToken = response.cardToken, customer = response.customer)
        }


        private suspend fun loadSessions(cardToken: String, customer: Customer) {
            if (customer !is Customer.ActivatedCustomer) {
                return
            }
            val history = sessionsRepository.getSessionsForCustomer(
                GetSessionsForCustomerRequest(customer.id)
            ).sessions

            syncDispatch(Message.CustomerActivatedLoaded(
                cardToken = cardToken,
                customer = customer,
                history = history,
            ))
        }

    }

    private object ReducerImpl : Reducer<CustomerInfoStore.State, Message> {
        override fun CustomerInfoStore.State.reduce(msg: Message): CustomerInfoStore.State {
            return when (msg) {
                is Message.CustomerActivatedLoaded -> msg.reduce()
                is Message.CustomerPreloaded -> msg.reduce()
                is Message.Error -> msg.reduce()
                is Message.Loading -> msg.reduce()
            }
        }

        private fun Message.CustomerPreloaded.reduce(): CustomerInfoStore.State =
            when (customer) {
                is Customer.InactivatedCustomer ->
                    CustomerInfoStore.State.CustomerInactivatedLoaded(
                        cardToken = cardToken,
                        customer = customer,
                    )

                is Customer.ActivatedCustomer ->
                    CustomerInfoStore.State.CustomerActivatedPreloaded(
                        cardToken = cardToken,
                        customer = customer,
                    )
            }


        private fun Message.CustomerActivatedLoaded.reduce(): CustomerInfoStore.State =
            CustomerInfoStore.State.CustomerActivatedLoaded(
                cardToken = cardToken,
                customer = customer,
                history = history,
            )


        private fun Message.Loading.reduce(): CustomerInfoStore.State =
            CustomerInfoStore.State.Loading()


        private fun Message.Error.reduce(): CustomerInfoStore.State {
            return when (exception) {
                is CustomersException.CardNotFoundException -> CustomerInfoStore.State.Error.CardNotFound
                is CustomersException.CustomerNotFoundException -> CustomerInfoStore.State.Error.CustomerNotFound
                is CustomersException.IllegalCardTokenException -> CustomerInfoStore.State.Error.IllegalCard
                else -> CustomerInfoStore.State.Error.Unknown
            }
        }

    }

    private sealed class Message {

        class Loading : Message()

        data class CustomerPreloaded(
            val cardToken: String,
            val customer: Customer,
        ) : Message()

        data class CustomerActivatedLoaded(
            val cardToken: String,
            val customer: Customer.ActivatedCustomer,
            val history: List<Session>,
        ) : Message()

        class Error(val exception: Exception) : Message()

    }
}
