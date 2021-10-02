package ru.shafran.cards.ui.component.cardsdetails.use

import android.util.Log
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.map
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.UsageDataModel
import ru.shafran.cards.data.employee.toModel
import ru.shafran.network.employee.EmployeesListStore

class CardUsageComponent(
    override val card: CardModel,
    private val onUse: (UsageDataModel) -> Unit,
    private val onCancel: () -> Unit,
    private val employeesStore: EmployeesListStore,
) : CardUsage {

    init {
        employeesStore.accept(EmployeesListStore.Intent.LoadEmployees)
    }

    override val employees =
        employeesStore.states.map { state ->
            Log.d("EmployeesDetailsCheck", state.toString())
            when (state) {
                is EmployeesListStore.State.EmployeesLoaded -> {
                    state.employees.map { it.toModel() }
                }
                else -> {
                    null
                }
            }
        }

    override fun onUse(data: UsageDataModel) {
        onUse.invoke(data)
    }

    override fun onCancel() {
        onCancel.invoke()
    }

}