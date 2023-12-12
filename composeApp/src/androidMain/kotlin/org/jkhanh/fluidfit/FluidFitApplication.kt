package org.jkhanh.fluidfit

import android.app.Application
import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import initKoinAndroid
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

class FluidFitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())

        initKoinAndroid(
            listOf(
                module {
                    single<Context> { this@FluidFitApplication }
                }
            )
        )
        Firebase.initialize(this)
    }
}