package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.customers.data.CreateCustomersRequest
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.customers.data.EditableCustomerData

interface GenerateCustomerStore :
    Store<GenerateCustomerStore.Intent, GenerateCustomerStore.State, GenerateCustomerStore.Label> {

    sealed class Intent {

        class LoadDetails(
            val request: EditableCustomerData? = null,
            val companyId: CompanyId,
        ) : Intent()

        data class GenerateCustomer(
            val request: EditableCustomerData,
            val companyId: CompanyId,
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
            abstract val request: CreateCustomersRequest

            class Unknown(override val request: CreateCustomersRequest) : Error()
        }

    }

    sealed class Label {}
}