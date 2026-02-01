
package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
    emailFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
    onEmailFocused: () -> Unit,
    onPasswordFocused: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onForgotPassword: () -> Unit,
    onToggleMode: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_padding)),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text(stringResource(R.string.label_email)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester)
                    .onFocusChanged { if (it.isFocused) onEmailFocused() }
            )

            Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(stringResource(R.string.label_password)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester)
                    .onFocusChanged { if (it.isFocused) onPasswordFocused() }
            )

            Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

            Button(
                onClick = onSubmit,
                enabled = !isLoading,
                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.spacing_xlarge), vertical = dimensionResource(id = R.dimen.spacing_small))
            ) {
                Text(
                    stringResource(
                        if (isLoginMode) R.string.action_login else R.string.action_register
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
                    if (isLoginMode) stringResource(id = R.string.action_register)
                    else stringResource(id = R.string.action_login)
                )
            }

            if (isLoading) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                CircularProgressIndicator()
            }
        }
    }
}
