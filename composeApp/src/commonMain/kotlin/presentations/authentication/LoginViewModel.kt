package presentations.authentication

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecases.LoginEmailPasswordUsecase
import usecases.SignUpEmailPasswordUsecase

data class LoginUiState(
    var isLoading: Boolean = false,
    var errorMessage: String = "",
    var isLoggedIn: Boolean = false
)

class LoginViewModel(
    private val loginEmailPasswordUsecase: LoginEmailPasswordUsecase,
    private val signUpEmailPasswordUsecase: SignUpEmailPasswordUsecase
): ScreenModel {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val firebaseAuth = Firebase.auth

    fun isLoggedIn() {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoggedIn = firebaseAuth.currentUser != null
            )
        }
    }

    fun loginWithEmailPassword(email: String, password: String) {
        screenModelScope.launch {
            setUiStateToLoggingIn()
            try {
                val response = loginEmailPasswordUsecase(email, password)
                if (response.user != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true
                    )
                }
            } catch (e: FirebaseException) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error while login. Please try again"
                )
            }
        }
    }

    fun signUpWithEmailAndPassword(email: String, password: String, reTypePassword: String) {
        if (password != reTypePassword) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Password not match"
            )
            return
        }
        screenModelScope.launch {
            setUiStateToLoggingIn()
            try {
                val response = signUpEmailPasswordUsecase(email, password)
                if (response.user != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true
                    )
                }
            }  catch (e: FirebaseException) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error while sign up. Please try again"
                )
            }
        }
    }

    fun loginWithGoogle() {
        screenModelScope.launch {
            setUiStateToLoggingIn()
        }
    }

    private fun setUiStateToLoggingIn() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = ""
        )
    }
}