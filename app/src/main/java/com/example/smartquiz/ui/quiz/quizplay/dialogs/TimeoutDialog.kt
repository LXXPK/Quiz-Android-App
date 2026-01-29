package com.example.smartquiz.ui.quiz.quizplay.dialogs

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun TimeoutDialog() {
    AlertDialog(
        onDismissRequest = {}, // ⛔ cannot dismiss
        title = {
            Text(
                text = "Time’s Up!",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = "Your quiz has been submitted automatically."
            )
        },
        confirmButton = {
            Button(onClick = {}) {
                Text("OK")
            }
        }
    )
}
