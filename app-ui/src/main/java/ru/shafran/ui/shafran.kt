package ru.shafran.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.common.main.Main
import ru.shafran.ui.main.MainUI
import ru.shafran.ui.theme.ShafranCardsTheme

@Composable
fun ShafranApp(component: Main) {
    ShafranCardsTheme {
        MainUI(component, modifier = Modifier.fillMaxSize())
    }
}
