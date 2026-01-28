package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SocialLoginSection(
    onGoogleSignIn: () -> Unit
) {
    Text("OR")

    Spacer(Modifier.height(16.dp))

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onGoogleSignIn
    ) {
        Text("Sign in with Google")
    }
}
