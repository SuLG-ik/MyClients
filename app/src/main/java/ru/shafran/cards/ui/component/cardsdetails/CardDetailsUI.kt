package ru.shafran.cards.ui.component.cardsdetails

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.crossfade
import ru.shafran.cards.ui.component.cardsdetails.activation.CardActivationUI
import ru.shafran.cards.ui.component.cardsdetails.deactivate.CardDeactivationUI
import ru.shafran.cards.ui.component.cardsdetails.error.CardErrorUI
import ru.shafran.cards.ui.component.cardsdetails.info.CardInfoUI
import ru.shafran.cards.ui.component.cardsdetails.use.CardUsageUI
import ru.shafran.cards.ui.component.loading.LoadingUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardDetailsUI(
    component: CardDetails,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) {
        if (it == ModalBottomSheetValue.Hidden) {
            component.onHide()
        }
        true
    }
    Box(modifier = modifier) {
        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    Children(
                        routerState = component.state,
                        animation = crossfade(tween(100, easing = LinearEasing))
                    ) {
                        when (val instance = it.instance) {
                            is CardDetails.Child.Activation ->
                                CardActivationUI(
                                    component = instance.activation,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                )
                            is CardDetails.Child.Info ->
                                CardInfoUI(
                                    component = instance.info,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                )

                            is CardDetails.Child.Loading ->
                                LoadingUI(
                                    component = instance.loading,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                )
                            is CardDetails.Child.Usage ->
                                CardUsageUI(
                                    component = instance.usage,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                )
                            is CardDetails.Child.Deactivation ->
                                CardDeactivationUI(
                                    component = instance.deactivation,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                )
                            is CardDetails.Child.Error ->
                                CardErrorUI(
                                    component = instance.error, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                )
                            CardDetails.Child.Hidden -> Text("")
                        }
                    }
                }
            },
            sheetElevation = 0.dp,
            sheetShape = MaterialTheme.shapes.large.copy(
                bottomEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp)
            ),
            content = content,
        )
    }

    val isShown by component.isShown.collectAsState()

    LaunchedEffect(key1 = isShown, block = {
        if (isShown)
            state.show()
        else {
            state.hide()
        }
    })


}