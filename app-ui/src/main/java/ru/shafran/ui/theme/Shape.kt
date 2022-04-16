package ru.shafran.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Shapes as Shapes3

internal val shapes = Shapes(
    small = RoundedCornerShape(20.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(20.dp),
)

internal val shapes3 = Shapes3(
    extraSmall = RoundedCornerShape(20.dp),
    small = RoundedCornerShape(20.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(20.dp),
)
