package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import com.example.smartquiz.R
import com.example.smartquiz.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoginMode by rememberSaveable { mutableStateOf(true) }
    var focusedField by rememberSaveable { mutableStateOf("email") }


    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }


    LaunchedEffect(authViewModel.errorMessage) {
        authViewModel.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            authViewModel.clearError()
        }
    }


    LaunchedEffect(authViewModel.loginSuccess) {
        if (authViewModel.loginSuccess) {
            onLoginSuccess()
            authViewModel.consumeLoginSuccess()
        }
    }


    LaunchedEffect(focusedField) {
        when (focusedField) {
            "email" -> emailFocusRequester.requestFocus()
            "password" -> passwordFocusRequester.requestFocus()
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
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.large_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginHeader(isLoginMode = isLoginMode)

            Spacer(Modifier.height(dimensionResource(id = R.dimen.large_padding)))

            LoginForm(
                email = email,
                password = password,
                isLoginMode = isLoginMode,
                isLoading = authViewModel.isLoading,
                emailFocusRequester = emailFocusRequester,
                passwordFocusRequester = passwordFocusRequester,
                onEmailFocused = { focusedField = "email" },
                onPasswordFocused = { focusedField = "password" },
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

            Spacer(Modifier.height(dimensionResource(id = R.dimen.large_padding)))

            SocialLoginSection(
                onGoogleSignIn = {
                    keyboardController?.hide()
                    scope.launch { authHandler.signIn() }
                }
            )
        }
    }
}
