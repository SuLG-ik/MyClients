package ru.shafran.network.session

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.session.data.CreateSessionForCustomerRequest

interface SessionActivationStore :
    Store<SessionActivationStore.Intent, SessionActivationStore.State, SessionActivationStore.Label> {


    sealed class Intent {

        data class LoadDetailsWithCustomer(
            val customer: Customer.ActivatedCustomer,
        ) : Intent()

        data class Activate(
            val request: CreateSessionForCustomerRequest,
        ) : Intent()

    }


    sealed class State {

        sealed class Error: State() {

            object Unknown : Error()

        }

        object Empty : State()

        data class ActivationLoading(
            val configuration: ConfiguredService,
        ) : State()

        class DetailsLoading : State()

        data class DetailsLoaded(
            val customer: Customer.ActivatedCustomer,
        ) : State()
    }

    sealed class Label {

        object ActivateCompleted : Label()

    }

}