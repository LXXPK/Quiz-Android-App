package com.example.smartquiz.ui.quiz.quizplay.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TimeoutDialog() {
    AlertDialog(
        onDismissRequest = {},
        icon = {
            Icon(
                imageVector = Icons.Outlined.TimerOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = "Time’s Up",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = "Your quiz has been submitted automatically.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            Button(
                enabled = false, // 🔒 intentional
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("Submitting…")
            }
        }
    )
}
