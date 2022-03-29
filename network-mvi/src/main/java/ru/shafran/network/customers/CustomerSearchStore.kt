package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.FoundCustomerItem
import ru.shafran.network.customers.data.SearchCustomerByPhoneRequest

interface CustomerSearchStore :
    Store<CustomerSearchStore.Intent, CustomerSearchStore.State, CustomerSearchStore.Label> {

    sealed class Intent {
        data class SearchCustomerByPhone(
            val request: SearchCustomerByPhoneRequest,
        ) : Intent()

    }

    sealed class State {

        object Empty : State()

        data class SearchCompleted(
            val searchResult: List<FoundCustomerItem>,
        ) : State()

        class Loading() : State()

        sealed class Error : State() {
            object Unknown : Error()
        }

    }

    sealed class Label {

    }
}