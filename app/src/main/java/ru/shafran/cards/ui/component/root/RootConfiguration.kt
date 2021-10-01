package ru.shafran.cards.ui.component.root

import android.os.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.cards.R
import ru.shafran.cards.utils.NavigationItem


sealed class RootConfiguration: Parcelable {

    @Parcelize
    object Cards : RootConfiguration()

    @Parcelize
    object History : RootConfiguration()

    @Parcelize
    object Employees : RootConfiguration()

    companion object{
        val children get() = mapOf(
            Cards to NavigationItem(
                "Карты",
                R.drawable.logo_cards,
            ),
            History to NavigationItem(
                "История",
                R.drawable.logo_tickets,
            ),
            Employees to NavigationItem(
                "Работники",
                R.drawable.logo_employees,
            )
        )
    }

}
