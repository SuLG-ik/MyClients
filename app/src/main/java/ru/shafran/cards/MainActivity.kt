package ru.shafran.cards

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.rememberRootComponent
import ru.shafran.cards.ui.component.main.Main
import ru.shafran.cards.ui.component.main.MainComponent
import ru.shafran.cards.ui.component.main.MainUI
import ru.shafran.cards.ui.theme.ShafranCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShafranCardsTheme {
                val scope = rememberCoroutineScope()
                val component = rememberRootComponent<ComponentActivity, Main> { MainComponent(it, scope) }
                MainUI(component = component, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

