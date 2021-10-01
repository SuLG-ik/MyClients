package ru.shafran.cards.ui.component.cardsdetails.use

import kotlinx.coroutines.flow.Flow
import ru.shafran.cards.data.card.CardModel
import ru.shafran.cards.data.card.UsageDataModel
import ru.shafran.cards.data.employee.EmployeeModel

interface CardUsage {

    val card: CardModel

    val employees: Flow<List<EmployeeModel>?>

    fun onUse(data: UsageDataModel)

    fun onCancel()

}