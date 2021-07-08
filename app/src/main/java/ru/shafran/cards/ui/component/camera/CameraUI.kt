package ru.shafran.cards.ui.component.camera

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.delay
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.CardDetailsUI
import ru.shafran.cards.ui.view.camera.CameraPreview
import ru.shafran.cards.ui.view.camera.rememberCameraState

@Composable
fun CameraUI(component: Camera, modifier: Modifier = Modifier) {
    val state = rememberCameraState()

    val isShown by component.details.isShown.subscribeAsState()
    LaunchedEffect(key1 = isShown, block = {
        if (isShown) component.onDisable()
        else component.onEnable()
    })

    val isEnabled by component.isEnabled.subscribeAsState()
    LaunchedEffect(key1 = isEnabled, block = {
        state.setEnabled(isEnabled)
    })

    LaunchedEffect(key1 = Unit, block = {
        delay(5000)
        component.details.onShow(DetectedCard("pososi"))
    })
    CardDetailsUI(component = component.details, modifier = Modifier.fillMaxWidth()) {
        CameraPreview(state, modifier = modifier)
    }
}