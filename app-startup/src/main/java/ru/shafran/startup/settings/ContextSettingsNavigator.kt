package ru.shafran.startup.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import ru.shafran.common.utils.SettingsNavigator

class ContextSettingsNavigator(
    private val context: Context
) : SettingsNavigator {

    override fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", context.packageName, null)
        context.startActivity(intent)
    }

}