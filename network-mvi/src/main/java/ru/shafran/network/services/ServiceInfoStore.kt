package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.services.data.Service

interface ServiceInfoStore :
    Store<ServiceInfoStore.Intent, ServiceInfoStore.State, ServiceInfoStore.Label> {

    sealed class Intent {

        data class LoadServiceWithId(
            val serviceId: String,
        ) : Intent()

        data class LoadServiceWithData(
            val service: Service,
        ) : Intent()


    }

    sealed class State {

        object Empty : State()

        class Loading : State()

        data class ServiceLoaded(
            val service: Service,
        ) : State()

        sealed class Error : State() {
            object Unknown : Error()
        }

    }

    sealed class Label

}