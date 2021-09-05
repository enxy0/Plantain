package kek.enxy.plantwriter

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kek.enxy.domain.settings.AppSettings
import kek.enxy.plantwriter.di.KoinModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    private val settings by inject<AppSettings>()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
        initKotpref()
        initSettings()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(KoinModules.all())
        }
    }

    private fun initLogger() {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    private fun initKotpref() {
        Kotpref.init(this)
    }

    private fun initSettings() {
        settings.init()
    }
}
