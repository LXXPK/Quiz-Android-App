package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.smartquiz.utils.isValidEmail
import com.example.smartquiz.utils.isValidPassword
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartquiz.viewmodel.auth.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current


//    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authViewModel.errorMessage) {
        authViewModel.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(authViewModel.loginSuccess) {
        if (authViewModel.loginSuccess) {
            onLoginSuccess()
            authViewModel.consumeLoginSuccess()
        }
    }


    val authHandler = remember {
        GoogleAuthHandler(
            context = context,
            onFirebaseUser = {
                authViewModel.onFirebaseLoginSuccess(it)

            },
            onError = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            }
        )
    }


        Box(
            modifier = Modifier
                .fillMaxSize(),

            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = if (isLoginMode) "Login" else "Register",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        keyboardController?.hide()
                        when {
                            email.isBlank() || password.isBlank() ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("Fields cannot be empty")
                                }

                            !isValidEmail(email) ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("Invalid email format")
                                }

                            !isValidPassword(password) ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("Password must be at least 6 characters")
                                }

                            else -> {
                                if (isLoginMode) {
                                    authViewModel.loginWithEmailPassword(email, password)
                                } else {
                                    authViewModel.registerWithEmailPassword(email, password)
                                }
                            }
                        }
                    }
                ) {
                    Text(if (isLoginMode) "Login" else "Register")
                }
                if (isLoginMode) {
                    TextButton(
                        onClick = {
                            keyboardController?.hide()
                            authViewModel.sendPasswordResetEmail(email)
                        }
                    ) {
                        Text("Forgot password?")
                    }
                }


                TextButton(onClick = { isLoginMode = !isLoginMode }) {
                    Text(
                        if (isLoginMode)
                            "No account? Register"
                        else
                            "Already have an account? Login"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("OR")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        keyboardController?.hide()
                        scope.launch { authHandler.signIn() }
                    }
                ) {
                    Text("Sign in with Google")
                }

                if (authViewModel.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            }

    }
}
