package com.example.smartquiz.ui.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginHeader(isLoginMode: Boolean) {
    Text(
        text = if (isLoginMode) "Login" else "Register",
        style = MaterialTheme.typography.headlineMedium
    )
}
