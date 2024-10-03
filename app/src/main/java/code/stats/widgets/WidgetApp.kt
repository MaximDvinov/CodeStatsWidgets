package code.stats.widgets

import android.app.Application
import code.stats.widget.di.widgetModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WidgetApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@WidgetApp)
            // Load modules
            modules(widgetModule)
        }
    }
}
