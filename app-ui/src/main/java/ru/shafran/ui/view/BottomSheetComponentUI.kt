package ru.shafran.ui.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.shafran.ui.utils.ValueEffect

@OptIn(ExperimentalMaterialApi::class, ExperimentalDecomposeApi::class)
@Composable
fun <Configuration : Any, Child : Any> BottomSheetComponentUI(
    router: Value<RouterState<Configuration, Child>>,
    isHidden: (Child) -> Boolean,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
    navHost: @Composable ((Child) -> Unit),
    content: @Composable (() -> Unit),
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) {
        if (it == ModalBottomSheetValue.Hidden) {
            onHide()
        }
        return@rememberModalBottomSheetState true
    }
    ValueEffect(router) {
        scope.launch(Dispatchers.Main) {
            try {
                if (isHidden(it.activeChild.instance)) {
                    state.animateTo(ModalBottomSheetValue.Hidden, tween(50))
                } else {
                    state.animateTo(ModalBottomSheetValue.Expanded)
                }
            } catch (e: Exception) {

            }
        }
    }
    Box(modifier = modifier) {
        ExtendedModalBottomSheet(
            sheetState = state,
            sheetContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Children(router) {
                        navHost(it.instance)
                    }
                }
            },
            sheetElevation = 0.dp,
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }
}