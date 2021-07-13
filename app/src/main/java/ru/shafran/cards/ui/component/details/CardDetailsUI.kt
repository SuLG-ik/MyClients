package ru.shafran.cards.ui.component.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.cards.ui.component.details.info.CardInfoUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardDetailsUI(
    component: CardDetails,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) {
        if (it == ModalBottomSheetValue.Hidden) {
            component.onHide()
        }
        true
    }
    val isShown by component.isShown.subscribeAsState()

    LaunchedEffect(key1 = isShown, block = {
        if (isShown)
            state.show()
        else {
            state.hide()
        }
    })

    ModalBottomSheetLayout(sheetState = state, sheetContent = {
        Column(modifier) {
            CardInfoUI(component = component.info, modifier = Modifier.fillMaxWidth().padding(15.dp))
        }
    }) {
        content()
    }
}