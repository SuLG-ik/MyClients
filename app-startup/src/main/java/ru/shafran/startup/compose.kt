package ru.shafran.startup

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.defaultComponentContext
import org.koin.android.ext.android.get
import ru.shafran.common.root.RootComponent
import ru.shafran.ui.ShafranApp
import ru.shafran.ui.utils.LocalTimeFormatter
import ru.shafran.ui.utils.TimeFormatter

fun ComponentActivity.setupShafran() {
    val timeFormatter = get<TimeFormatter>()
    val component = RootComponent(
        componentContext = defaultComponentContext(),
        onOpenSettings = this::onOpenSettings,
    )
    setContent {
        CompositionLocalProvider(LocalTimeFormatter provides timeFormatter) {
            ShafranApp(component)
        }
    }
}

private fun Context.onOpenSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", packageName, null)
    startActivity(intent)
}
