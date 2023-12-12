package presentations.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import presentations.main.MainScreen

class LoginScreen: Screen {
    
    @Composable
    override fun Content() {
        Login()
    }

    @Composable
    fun Login() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<LoginViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        var showSignIn by remember {
            mutableStateOf(true)
        }
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var reTypePassword by remember {
            mutableStateOf("")
        }
        var passwordVisible by remember {
            mutableStateOf(false)
        }
        var reTypePasswordVisible by remember {
            mutableStateOf(false)
        }
        val isValidInput = email.isNotEmpty() && password.isNotEmpty()

        if (uiState.isLoggedIn) {
            navigator?.push(MainScreen())
        }

        viewModel.isLoggedIn()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Log in to FluidFit", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = "Toggle password visibility")
                    }
                }
            )

            if (!showSignIn) {
                OutlinedTextField(
                    value = reTypePassword,
                    onValueChange = {reTypePassword = it},
                    label = { Text("Re-type Password") },
                    visualTransformation = if (reTypePasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        val icon = if (reTypePasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { reTypePasswordVisible = !reTypePasswordVisible }) {
                            Icon(imageVector = icon, contentDescription = "Toggle password visibility")
                        }
                    }
                )

                Text("Already have an account?",
                    modifier = Modifier.clickable {
                        showSignIn = !showSignIn
                    }.padding(horizontal = 16.dp, vertical = 8.dp))
            } else {
                Text("Don't have an account?",
                    modifier = Modifier.clickable {
                        showSignIn = !showSignIn
                    }.padding(horizontal = 16.dp, vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
            if (showSignIn) {
                ActionButton(
                    "Login",
                    !uiState.isLoading && isValidInput
                ) { viewModel.loginWithEmailPassword(email.trim(), password.trim()) }
            } else {
                val isValidSignUpInput = email.isNotEmpty() && password.isNotEmpty() && reTypePassword.isNotEmpty()

                ActionButton(
                    "Sign Up",
                    !uiState.isLoading && isValidSignUpInput
                ) {
                    viewModel.signUpWithEmailAndPassword(email, password, reTypePassword)
                }
            }

            if (uiState.errorMessage.isNotEmpty()) {
                Text(text = uiState.errorMessage, color = Color.Red.copy(
                    alpha = 0.8f
                ))
            }

            Button(
                onClick = { /* Login using GG */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login with Google")
            }
        }
    }

    @Composable
    fun ActionButton(
        text: String,
        isEnable: Boolean,
        action: () -> Unit
    ) {
        Button(
            onClick = action,
            modifier = Modifier.fillMaxWidth(),
            enabled = isEnable
        ) {
            Text(text)
        }
    }
}