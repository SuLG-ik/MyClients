package ru.shafran.startup

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import org.koin.android.ext.android.get
import ru.shafran.common.main.Main
import ru.shafran.common.main.MainComponent
import ru.shafran.startup.settings.ContextSettingsNavigator
import ru.shafran.startup.share.ContextShare
import ru.shafran.ui.ShafranApp
import ru.shafran.ui.utils.LocalTimeFormatter

fun ComponentActivity.setupShafran() {
    val share = ContextShare(this)
    val settingsNavigator = ContextSettingsNavigator(this)
    val component: Main = MainComponent(
        componentContext = defaultComponentContext(),
        share = share,
        settingsNavigator = settingsNavigator
    )
    installSplashScreen().setKeepOnScreenCondition { component.isLoading.value }
    setContent {
        CompositionLocalProvider(LocalTimeFormatter provides get()) {
            ShafranApp(component)
        }
    }
}

