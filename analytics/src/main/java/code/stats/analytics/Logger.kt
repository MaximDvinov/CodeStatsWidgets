package code.stats.analytics

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

interface Logger {
    fun log(message: String)
    fun log(tag: String, message: String)
}

object AppLogger : Logger {
    override fun log(message: String) {
        Log.i("AppLogger", "log: $message")
        Firebase.crashlytics.recordException(object : Exception(message) {})
    }

    Теперь интересно, как реализовать сохранение этих данных, чтобы они не пропали в случае закрытия
    и их можно было бы отправить в следующий раз?

    override fun log(tag: String, message: String) {
        Log.i(tag, message)
        Firebase.crashlytics.recordException(object : Exception("$tag: $message") {})
    }
}
