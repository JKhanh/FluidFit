package di

import org.koin.dsl.module
import presentations.authentication.LoginViewModel
import usecases.LoginEmailPasswordUsecase
import usecases.SignUpEmailPasswordUsecase

val loginModule = module {
    factory { LoginEmailPasswordUsecase() }
    factory { SignUpEmailPasswordUsecase() }
    factory { LoginViewModel(get(), get()) }
}