package ru.shafran.cards

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component: Main = MainComponent(
            defaultComponentContext(),
            onOpenSettings = this::onOpenSettings
        )
        setContent {
            ShafranCardsTheme {
                MainUI(component = component, modifier = Modifier.fillMaxSize())
            }
        }
    }

    private fun onOpenSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }

}

