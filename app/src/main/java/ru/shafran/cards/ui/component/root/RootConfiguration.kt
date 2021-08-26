package ru.shafran.cards.ui.component.root

import android.os.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.cards.R
import ru.shafran.cards.utils.NavigationItem


sealed class RootConfiguration: Parcelable {

    @Parcelize
    object Tickets : RootConfiguration()

    @Parcelize
    object Employees : RootConfiguration()

    @Parcelize
    object Services : RootConfiguration()

    @Parcelize
    object Cards : RootConfiguration()

    companion object{
        val children get() = mapOf(
            Tickets to NavigationItem(
                "Записи",
                R.drawable.logo_tickets,
            ),
            Employees to NavigationItem(
                "Работники",
                R.drawable.logo_employees,
            ),
            Services to NavigationItem(
                "Услуги",
                R.drawable.logo_services,
            ),
            Cards to NavigationItem(
                "Карты",
                R.drawable.logo_cards,
            )
        )
    }

}
