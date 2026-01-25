package com.example.smartquiz.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    userEmail: String? = null,
    onProfile: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "🎉 Login Successful",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            userEmail?.let {
                Text(
                    text = "Logged in as: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onProfile) {
                Text("Go to Profile")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            userEmail = "omkar@gmail.com",
            onProfile = {},
            onLogout = {}
        )
    }
}
