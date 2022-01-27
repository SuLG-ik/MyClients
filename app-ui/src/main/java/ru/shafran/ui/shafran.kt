package ru.shafran.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shafran.common.root.Root
import ru.shafran.ui.root.RootUI
import ru.shafran.ui.theme.ShafranCardsTheme

@Composable
fun ShafranApp(component: Root) {
    ShafranCardsTheme {
        RootUI(component, modifier = Modifier.fillMaxSize())
    }
}
