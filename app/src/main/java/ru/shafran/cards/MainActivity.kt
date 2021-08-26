package ru.shafran.cards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import ru.shafran.cards.ui.component.main.Main
import ru.shafran.cards.ui.component.main.MainComponent
import ru.shafran.cards.ui.component.main.MainUI
import ru.shafran.cards.ui.theme.ShafranCardsTheme

class MainActivity : ComponentActivity() {

    val xPlayer = 2
    val yPlayer = 3

    val xBox = 1
    val yBox = 3

    val hBox = 2
    val wBox = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component: Main = MainComponent(defaultComponentContext())
        setContent {
            ShafranCardsTheme {
                MainUI(component = component, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

