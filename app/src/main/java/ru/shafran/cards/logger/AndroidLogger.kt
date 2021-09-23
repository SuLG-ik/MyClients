package ru.shafran.cards.logger

import android.util.Log
import ru.sulgik.logger.Logger

private class AndroidLogger : Logger {
    override fun debug(tag: String, message: String, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun warning(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    override fun wtf(tag: String, message: String, throwable: Throwable?) {
        Log.wtf(tag, message, throwable)
    }
}


fun Logger(): Logger {
    return AndroidLogger()
}