package ru.shafran.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExtendedModalBottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(
                    modifier = modifier
                        .height(10.dp)
                )
                MaterialDivider(modifier = Modifier.fillMaxWidth())
                sheetContent()
            }
        },
        modifier = modifier,
        sheetShape = MaterialTheme.shapes.large
            .calculateTopShape(sheetState.offset.value)
            .copy(bottomStart = ZeroCornerSize, bottomEnd = ZeroCornerSize),
        sheetElevation = sheetElevation,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContentColor = sheetContentColor,
        scrimColor = scrimColor,
        content = content,
    )
}

@Composable
private fun CornerBasedShape.calculateTopShape(offset: Float): CornerBasedShape {
    return copy(calculateShape(min = 0.dp, max = 20.dp, offset = offset * 2.5f))
}

@Composable
private fun calculateShape(min: Dp, max: Dp, offset: Float): CornerSize {
    return remember(offset, min, max) { CornerSize(offset.coerceIn(min.value, max.value)) }
}