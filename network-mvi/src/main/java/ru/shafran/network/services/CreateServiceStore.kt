package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.services.data.CreateServiceRequest
import ru.shafran.network.services.data.Service

interface CreateServiceStore :
    Store<CreateServiceStore.Intent, CreateServiceStore.State, CreateServiceStore.Label> {

    sealed class Intent {

        data class LoadDetails(val request: CreateServiceRequest? = null) : Intent()

        data class CreateService(
            val request: CreateServiceRequest,
        ) : Intent()

    }

    sealed class State {

        data class CreateService(
            val request: CreateServiceRequest? = null,
        ): State()

        data class CreateServiceLoading(val request: CreateServiceRequest) : State()

        sealed class Error : State() {
            abstract val request: CreateServiceRequest

            data class Unknown(override val request: CreateServiceRequest) : Error()
        }

    }

    sealed class Label {

        data class OnServiceCreated(val service: Service) : Label()

    }


}