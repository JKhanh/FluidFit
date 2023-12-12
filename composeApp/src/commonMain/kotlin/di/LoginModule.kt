package di

import org.koin.dsl.module
import presentations.authentication.LoginViewModel

val loginModule = module {
    factory { LoginViewModel() }
}