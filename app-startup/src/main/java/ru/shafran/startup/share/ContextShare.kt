package ru.shafran.startup.share

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.widget.Toast
import androidx.core.content.FileProvider
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shafran.common.utils.Share
import java.io.File


class ContextShare(private val context: Context) : Share {
    @SuppressLint("SetWorldReadable")
    override suspend fun shareBitmap(bitmap: Bitmap) {
        try {
            withContext(Dispatchers.IO) {
                val outputFile = File(context.cacheDir, "share.png")
                val stream = outputFile.outputStream()
                bitmap.compress(CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
                outputFile.setReadable(true, false)
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_STREAM,
                    FileProvider.getUriForFile(context, context.packageName, outputFile))
                shareIntent.type = "image/png"
                withContext(Dispatchers.Main) {
                    context.startActivity(Intent.createChooser(shareIntent, "Share File"))
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
            Napier.w("Error while share bitmap", throwable = e)
        }
    }


}