package org.jkhanh.fluidfit

import org.koin.dsl.module

actual val platformModule = module {
    single { DriverFactory() }
}