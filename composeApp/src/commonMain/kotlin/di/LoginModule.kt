package di

import org.koin.dsl.module
import presentations.LoginViewModel

val loginModule = module {
    factory { LoginViewModel() }
}