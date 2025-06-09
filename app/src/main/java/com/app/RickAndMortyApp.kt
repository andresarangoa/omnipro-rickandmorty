package com.app

import android.app.Application
import com.app.omnipro_test_rm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class RickAndMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickAndMortyApp)
            loadKoinModules(getKoinModules())
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    private fun getKoinModules() = listOf(
        appModule
    )

}