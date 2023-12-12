
import org.jkhanh.fluidfit.appModule
import org.jkhanh.fluidfit.platformModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoinAndroid(additionalModules: List<Module>) {
    startKoin {
        modules(additionalModules + getBaseModules())
    }
}

fun initKoiniOS() {
    initKoin(emptyList())
}

internal fun getBaseModules() = appModule + platformModule

fun initKoin(additionalModules: List<Module>){
    startKoin {
        modules(additionalModules + getBaseModules())
    }
}