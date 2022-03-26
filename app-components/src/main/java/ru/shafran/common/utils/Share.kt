package ru.shafran.common.utils

import android.graphics.Bitmap

interface Share {

    suspend fun shareBitmap(bitmap: Bitmap)

}