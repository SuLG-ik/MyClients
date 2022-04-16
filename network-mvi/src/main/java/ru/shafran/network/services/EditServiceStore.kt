package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.services.data.EditServiceRequest
import ru.shafran.network.services.data.EditableServiceData
import ru.shafran.network.services.data.Service

interface EditServiceStore :
    Store<EditServiceStore.Intent, EditServiceStore.State, EditServiceStore.Label> {

    sealed class Intent {

        data class LoadDetails(val request: EditableServiceData) : Intent()

        data class EditService(
            val request: EditServiceRequest,
        ) : Intent()

    }

    sealed class State {

        object Empty : State()

        data class EditService(
            val request: EditableServiceData,
        ) : State()

        data class EditServiceLoading(val request: EditServiceRequest) : State()

        sealed class Error : State() {
            abstract val request: EditServiceRequest

            data class Unknown(override val request: EditServiceRequest) : Error()
        }

    }

    sealed class Label {

        data class OnServiceEdited(val service: Service) : Label()

    }


}