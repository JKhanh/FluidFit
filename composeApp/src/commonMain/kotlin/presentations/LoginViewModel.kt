package presentations

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    var isLoading: Boolean = false,
    var errorMessage: String = "",
    var isLoggedIn: Boolean = false
)

class LoginViewModel: ScreenModel {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginWithEmailPassword(email: String, password: String) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            delay(1500)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "Stub Method"
            )
        }
    }
}