package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.CreateCustomerRequest
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.CustomerData
import ru.shafran.network.customers.data.EditableCustomerData

interface GenerateCustomerStore :
    Store<GenerateCustomerStore.Intent, GenerateCustomerStore.State, GenerateCustomerStore.Label> {

    sealed class Intent {

        class LoadDetails(
            val request: EditableCustomerData? = null,
        ) : Intent()

        data class GenerateCustomer(
            val request: CreateCustomerRequest,
        ) : Intent()

    }

    sealed class State {

        class Request(val data: EditableCustomerData?) : State()

        class Loading() : State()

        class CustomerGenerated(
            val token: String,
            val customer: Customer.ActivatedCustomer,
        ) : State()

        sealed class Error : State() {
            abstract val request: CreateCustomerRequest

            class Unknown(override val request: CreateCustomerRequest) : Error()
        }

    }

    sealed class Label {}
}