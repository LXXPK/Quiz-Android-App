package com.example.smartquiz.ui.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R

@Composable
fun LoginHeader(isLoginMode: Boolean) {
    Text(
        text = stringResource(
            if (isLoginMode)
                R.string.action_login
            else
                R.string.action_register
        ),
        style = MaterialTheme.typography.headlineMedium
    )
}
