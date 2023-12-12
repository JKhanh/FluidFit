package di

import org.koin.dsl.module
import presentations.main.ActivityRepository
import presentations.main.ActivityRepositoryImpl
import presentations.main.MainViewModel

val mainModule = module {
    factory<ActivityRepository> { ActivityRepositoryImpl(get()) }
    factory { MainViewModel(get()) }
}