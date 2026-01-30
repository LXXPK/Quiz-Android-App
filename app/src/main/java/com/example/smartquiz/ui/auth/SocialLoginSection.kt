package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R

@Composable
fun SocialLoginSection(
    onGoogleSignIn: () -> Unit
) {
    Text(stringResource(R.string.label_or))

    Spacer(Modifier.height(12.dp))

    Button(
        onClick = onGoogleSignIn,
        modifier = Modifier.fillMaxWidth(0.70f)
    ) {
        Text(stringResource(R.string.action_sign_in_google))
    }
}
