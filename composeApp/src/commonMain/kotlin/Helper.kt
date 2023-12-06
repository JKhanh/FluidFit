import di.loginModule
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(loginModule)
    }
}