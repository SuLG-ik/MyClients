package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.services.data.CreateConfigurationRequest
import ru.shafran.network.services.data.CreateConfigurationRequestData
import ru.shafran.network.services.data.Service

interface CreateServiceConfigurationStore :
    Store<CreateServiceConfigurationStore.Intent, CreateServiceConfigurationStore.State, CreateServiceConfigurationStore.Label> {

    sealed class Intent {

        data class LoadDetails(val request: CreateConfigurationRequestData? = null) : Intent()

        data class CreateConfiguration(
            val request: CreateConfigurationRequest,
        ) : Intent()

    }

    sealed class State {

        object Empty : State()

        data class CreateConfiguration(
            val request: CreateConfigurationRequestData? = null,
        ) : State()

        data class CreateConfigurationLoading(val request: CreateConfigurationRequest) : State()

        sealed class Error : State() {
            abstract val request: CreateConfigurationRequest

            data class Unknown(override val request: CreateConfigurationRequest) : Error()
        }

    }

    sealed class Label {

        data class OnConfigurationCreated(val service: Service) : Label()

    }


}