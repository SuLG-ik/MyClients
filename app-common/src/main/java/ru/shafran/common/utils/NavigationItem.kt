package ru.shafran.common.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavigationItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
)