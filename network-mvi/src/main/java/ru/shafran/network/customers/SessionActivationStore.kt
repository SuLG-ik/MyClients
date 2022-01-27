package ru.shafran.network.customers

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.customers.data.Customer
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.services.data.Service
import ru.shafran.network.session.data.CreateSessionForCustomerRequest

interface SessionActivationStore :
    Store<SessionActivationStore.Intent, SessionActivationStore.State, SessionActivationStore.Label> {


    sealed class Intent {

        data class LoadDetailsWithId(
            val employeeId: String,
        ) : Intent()

        data class Activate(
            val request: CreateSessionForCustomerRequest,
        ) : Intent()

    }


    sealed class State {

        data class ActivationLoading(
            val configuration: ConfiguredService,
        ) : State()

        class DetailsLoading : State()

        data class DetailsLoaded(
            val customer: Customer.ActivatedCustomer,
            val services: List<Service>,
            val employees: List<Employee>,
        ) : State()
    }

    sealed class Label {

        object ActivateCompleted : Label()

    }

}