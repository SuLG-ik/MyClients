package ru.shafran.cards.utils

import com.google.zxing.common.BitMatrix
import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

fun BitMatrix.toBitmap(isDark: Boolean = false): Bitmap {
    return if (isDark) toBitmap(Color.WHITE, Color.BLACK) else toBitmap(Color.BLACK, Color.WHITE)
}

fun BitMatrix.toBitmap(mainColor: Int, backgroundColor: Int): Bitmap {
    val bmp = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until width) {
            bmp.setPixel(y, x, if (this[x, y]) mainColor else backgroundColor)
        }
    }
    return bmp
}

@Composable
fun rememberQrCodeImageBitmap(content: String, width: Int = 20, height: Int = 20): ImageBitmap {
    val mainColor = LocalContentColor.current.value.toInt()
    val backgroundColor = MaterialTheme.colors.background.value.toInt()
    return remember {
        val matrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height)
        matrix.toBitmap(mainColor, backgroundColor).asImageBitmap()
    }
}