package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.CreateCustomerRequest
import ru.shafran.network.customers.data.Customer

interface GenerateCustomerStore :
    Store<GenerateCustomerStore.Intent, GenerateCustomerStore.State, GenerateCustomerStore.Label> {

    sealed class Intent {

        data class GenerateCustomer(
            val request: CreateCustomerRequest,
        ) : Intent()

    }

    sealed class State {

        class Request() : State()

        class Loading() : State()

        class CustomerGenerated(
            val token: String,
            val customer: Customer.ActivatedCustomer
        ) : State()

        sealed class Error : State() {
            object Unknown : Error()
        }

    }

    sealed class Label {}
}