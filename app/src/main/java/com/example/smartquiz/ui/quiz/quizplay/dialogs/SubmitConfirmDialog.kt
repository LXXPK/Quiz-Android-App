package com.example.smartquiz.ui.quiz.quizplay.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun SubmitConfirmDialog(
    viewModel: QuizPlayViewModel
) {
    val remainingTime by viewModel.remainingTimeText.collectAsState()
    val attempted by viewModel.attemptedCount.collectAsState()
    val unattempted by viewModel.unattemptedCount.collectAsState()

    AlertDialog(
        onDismissRequest = viewModel::closeSubmitDialog,
        title = {
            Text(
                text = "Submit Quiz?",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("⏰ Time Left: $remainingTime")
                Text("✅ Attempted: $attempted")
                Text("❌ Unattempted: $unattempted")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.submitQuiz(
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = viewModel::closeSubmitDialog) {
                Text("Cancel")
            }
        }
    )
}
