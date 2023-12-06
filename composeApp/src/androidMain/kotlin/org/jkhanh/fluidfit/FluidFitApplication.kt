package org.jkhanh.fluidfit

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import di.loginModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin

class FluidFitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())

        startKoin {
            modules(loginModule)
        }
        Firebase.initialize(this)
    }
}