package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.smartquiz.R

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
        label = { Text(stringResource(R.string.label_email)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(
        modifier = Modifier.height(
            dimensionResource(R.dimen.spacing_small)
        )
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(R.string.label_password)) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(
        modifier = Modifier.height(
            dimensionResource(R.dimen.spacing_large)
        )
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSubmit,
        enabled = !isLoading
    ) {
        Text(
            stringResource(
                if (isLoginMode)
                    R.string.action_login
                else
                    R.string.action_register
            )
        )
    }

    if (isLoginMode) {
        TextButton(onClick = onForgotPassword) {
            Text(stringResource(R.string.action_forgot_password))
        }
    }

    TextButton(onClick = onToggleMode) {
        Text(
            stringResource(
                if (isLoginMode)
                    R.string.toggle_to_register
                else
                    R.string.toggle_to_login
            )
        )
    }

    if (isLoading) {
        Spacer(
            modifier = Modifier.height(
                dimensionResource(R.dimen.spacing_medium)
            )
        )
        CircularProgressIndicator()
    }
}
