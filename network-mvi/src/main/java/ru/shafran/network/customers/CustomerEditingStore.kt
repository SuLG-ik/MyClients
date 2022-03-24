package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.EditCustomerRequest

interface CustomerEditingStore :
    Store<CustomerEditingStore.Intent, CustomerEditingStore.State, CustomerEditingStore.Label> {

    sealed class Intent {
        data class LoadDetails(
            val customerId: String,
        ) : Intent()

        data class Edit(
            val request: EditCustomerRequest,
        ) : Intent()

    }

    sealed class State {

        object Empty: State()

        data class DetailsLoaded(
            val customer: Customer,
        ) : State()

        class Loading() : State()
        
        sealed class Error: State() {
            object Unknown: Error()
        }

    }

    sealed class Label {

        object EditCompleted : Label()

    }
}