package com.example.smartquiz.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R

@Composable
fun SocialLoginSection(
    onGoogleSignIn: () -> Unit
) {
    Text(
        text = stringResource(R.string.label_or)
    )

    Spacer(
        modifier = Modifier.height(
            dimensionResource(R.dimen.spacing_medium)
        )
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onGoogleSignIn
    ) {
        Text(
            text = stringResource(R.string.action_sign_in_google)
        )
    }
}
