package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch
import com.example.smartquiz.utils.*
import androidx.compose.material3.*

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    /* ---------- SIDE EFFECTS ---------- */

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
            onFirebaseUser = authViewModel::onFirebaseLoginSuccess,
            onError = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginHeader(isLoginMode = isLoginMode)

            Spacer(Modifier.height(20.dp))

            LoginForm(
                email = email,
                password = password,
                isLoginMode = isLoginMode,
                isLoading = authViewModel.isLoading,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onSubmit = {
                    keyboardController?.hide()

                    if (isLoginMode) {
                        authViewModel.loginWithEmailPassword(email, password)
                    } else {
                        authViewModel.registerWithEmailPassword(email, password)
                    }

                },
                onForgotPassword = {
                    keyboardController?.hide()
                    authViewModel.sendPasswordResetEmail(email)
                },
                onToggleMode = {
                    isLoginMode = !isLoginMode
                }
            )

            Spacer(Modifier.height(24.dp))

            SocialLoginSection(
                onGoogleSignIn = {
                    keyboardController?.hide()
                    scope.launch { authHandler.signIn() }
                }
            )
        }
    }
}
