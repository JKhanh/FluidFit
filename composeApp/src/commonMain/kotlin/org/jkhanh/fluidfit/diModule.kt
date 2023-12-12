package org.jkhanh.fluidfit

import di.loginModule
import di.mainModule
import org.koin.core.module.Module

expect val platformModule: Module

val appModule = loginModule + mainModule