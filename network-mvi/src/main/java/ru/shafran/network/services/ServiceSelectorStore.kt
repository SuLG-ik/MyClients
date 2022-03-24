package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.services.data.Service

interface ServiceSelectorStore :
    Store<ServiceSelectorStore.Intent, ServiceSelectorStore.State, ServiceSelectorStore.Label> {

    sealed class Intent {

        class LoadServicesList() : Intent()

    }

    sealed class State {

        class Loading() : State()

        class ServicesListLoaded(
            val services: List<Service>,
        ) : State()

    }

    sealed class Label

}