package di

import org.koin.dsl.module
import presentations.main.ActivityRepository
import presentations.main.ActivityRepositoryImpl
import presentations.main.MainViewModel
import presentations.main.ReminderRepository
import presentations.main.ReminderRepositoryImpl
import usecases.GetActivityListUsecase

val mainModule = module {
    factory<ActivityRepository> { ActivityRepositoryImpl(get()) }
    factory<ReminderRepository> { ReminderRepositoryImpl(get()) }
    factory { GetActivityListUsecase(get()) }
    factory { MainViewModel(get()) }
}