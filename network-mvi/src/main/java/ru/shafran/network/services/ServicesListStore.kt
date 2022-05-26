package ru.shafran.network.services

import com.arkivanov.mvikotlin.core.store.Store
import ru.shafran.network.companies.data.CompanyId
import ru.shafran.network.services.data.Service

interface ServicesListStore :
    Store<ServicesListStore.Intent, ServicesListStore.State, ServicesListStore.Label> {

    sealed class Intent {
        class LoadServices(
            val offset: Int = 30,
            val page: Int = 0,
            val companyId: CompanyId,
        ) : Intent()
    }

    sealed class State {

        object Empty : State()

        data class ServicesLoaded(
            val services: List<Service>,
        ) : State()

        data class Loading(
            val loadedServices: List<Service>? = null,
        ) : State()

        sealed class Error : State() {
            object ConnectionLost : Error()
            object Internal : Error()
            object Unknown : Error()
        }

    }

    sealed class Label {}

}