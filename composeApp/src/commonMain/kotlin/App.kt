
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentations.authentication.LoginScreen
import presentations.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(LoginScreen())
    }
}