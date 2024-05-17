package ru.andrewkir.tollroadcalcandroid

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.positioning.DefaultLocationSource
import ru.dgis.sdk.positioning.registerPlatformLocationSource
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}