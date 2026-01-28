package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    email: String,
    password: String,
    isLoginMode: Boolean,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onForgotPassword: () -> Unit,
    onToggleMode: () -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(12.dp))

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(20.dp))

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSubmit,
        enabled = !isLoading
    ) {
        Text(if (isLoginMode) "Login" else "Register")
    }

    if (isLoginMode) {
        TextButton(onClick = onForgotPassword) {
            Text("Forgot password?")
        }
    }

    TextButton(onClick = onToggleMode) {
        Text(
            if (isLoginMode)
                "No account? Register"
            else
                "Already have an account? Login"
        )
    }

    if (isLoading) {
        Spacer(Modifier.height(16.dp))
        CircularProgressIndicator()
    }
}
