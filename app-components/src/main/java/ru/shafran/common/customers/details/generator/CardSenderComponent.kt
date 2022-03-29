package ru.shafran.common.customers.details.generator

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.arkivanov.decompose.ComponentContext
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import com.google.zxing.qrcode.encoder.QRCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.shafran.common.utils.Share
import ru.shafran.common.utils.createCoroutineScope
import ru.shafran.network.customers.data.Customer


private val map = mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")


class CardSenderComponent(
    componentContext: ComponentContext,
    override val token: String,
    override val customer: Customer.ActivatedCustomer,
    private val onProfile: (Customer.ActivatedCustomer) -> Unit,
    private val share: Share,
) : CardSender, ComponentContext by componentContext {

    private val scope = createCoroutineScope()

    override fun onProfile() {
        onProfile.invoke(customer)
    }

    override fun onShare() {
        scope.launch(Dispatchers.IO) {
            val qr = Encoder.encode(token, ErrorCorrectionLevel.M, map)
            val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
            Canvas(bitmap).renderQRImage(qr, 512f, 512f, 2f)
            share.shareBitmap(bitmap)
        }
    }

    private fun Canvas.renderQRImage(
        code: QRCode,
        width: Float,
        height: Float,
        quietZone: Float,
    ) {
        drawColor(Color.WHITE)
        val input = code.matrix ?: throw IllegalStateException()
        val inputWidth = input.width
        val inputHeight = input.height
        val qrWidth = inputWidth + quietZone * 2
        val qrHeight = inputHeight + quietZone * 2
        val outputWidth = width.coerceAtLeast(qrWidth)
        val outputHeight = height.coerceAtLeast(qrHeight)
        val multiple = (outputWidth / qrWidth).coerceAtMost(outputHeight / qrHeight)
        val leftPadding = ((outputWidth - inputWidth * multiple) / 2)
        val topPadding = ((outputHeight - inputHeight * multiple) / 2)
        val FINDER_PATTERN_SIZE = 7
        val CIRCLE_SCALE_DOWN_FACTOR = 21f / 30f
        val circleSize = (multiple * CIRCLE_SCALE_DOWN_FACTOR)
        var inputY = 0
        var outputY = topPadding
        while (inputY < inputHeight) {
            var inputX = 0
            var outputX = leftPadding
            while (inputX < inputWidth) {
                if (input[inputX, inputY].toInt() == 1) {
                    if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE || inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE || inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
                        drawRoundRect(
                            RectF(
                                outputX,
                                outputY,
                                outputX + circleSize,
                                outputY + circleSize
                            ),
                            2f,
                            2f,
                            Paint().apply { color = Color.BLACK }
                        )
                    }
                }
                inputX++
                outputX += multiple
            }
            inputY++
            outputY += multiple
        }
        val circleDiameter = multiple * FINDER_PATTERN_SIZE
        drawFinderPatternCircleStyle(
            leftPadding,
            topPadding,
            circleDiameter
        )
        drawFinderPatternCircleStyle(
            leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
            topPadding,
            circleDiameter
        )
        drawFinderPatternCircleStyle(
            leftPadding,
            topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
            circleDiameter
        )
    }

    private fun Canvas.drawFinderPatternCircleStyle(
        x: Float,
        y: Float,
        circleDiameter: Float,
    ) {
        val WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7
        val WHITE_CIRCLE_OFFSET = circleDiameter / 7
        val middleDiameter = circleDiameter * 3 / 7
        val middleOffset = circleDiameter * 2 / 7
        drawRoundRect(
            RectF(
                x,
                y,
                x + circleDiameter,
                y + circleDiameter
            ),
            25f,
            25f,
            Paint().apply { color = Color.BLACK }
        )
        drawRoundRect(
            RectF(
                x + WHITE_CIRCLE_OFFSET,
                y + WHITE_CIRCLE_OFFSET,
                x + WHITE_CIRCLE_OFFSET + WHITE_CIRCLE_DIAMETER,
                y + WHITE_CIRCLE_OFFSET + WHITE_CIRCLE_DIAMETER,
            ),
            15f,
            15f,
            Paint().apply { color = Color.WHITE }
        )
        drawRoundRect(
            RectF(
                x + middleOffset,
                y + middleOffset,
                x + middleDiameter + middleOffset,
                y + middleDiameter + middleOffset,
            ),
            7.5f,
            7.5f,
            Paint().apply { color = Color.BLACK }
        )
    }

}