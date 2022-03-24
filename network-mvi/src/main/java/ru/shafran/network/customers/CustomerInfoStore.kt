package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.session.data.Session

interface CustomerInfoStore :
    Store<CustomerInfoStore.Intent, CustomerInfoStore.State, CustomerInfoStore.Label> {

    sealed class Intent {

        data class LoadCustomerWithToken(
            val customerToken: String,
        ) : Intent()

    }

    sealed class State {

        object Empty: State()

        class Loading : State()

        data class CustomerInactivatedLoaded(
            val customer: Customer.InactivatedCustomer
        ) : State()

        data class CustomerActivatedPreloaded(
            val customer: Customer.ActivatedCustomer,
        ) : State()

        data class CustomerActivatedLoaded(
            val customer: Customer.ActivatedCustomer,
            val history: List<Session>,
        ) : State()

        sealed class Error: State() {
            object IllegalCard: Error()
            object CardNotFound: Error()
            object CustomerNotFound: Error()
            object Unknown: Error()
        }

    }

    sealed class Label

}