package kek.enxy.plantwriter

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kek.enxy.plantwriter.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
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
}
