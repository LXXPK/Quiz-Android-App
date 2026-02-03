package com.example.smartquiz.ui.quiz.quizplay.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        icon = {
            Icon(
                imageVector = Icons.Outlined.WarningAmber,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = "Submit Quiz?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Once submitted, you won’t be able to change your answers.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        InfoRow("⏰ Time left", remainingTime)
                        InfoRow("✅ Attempted", attempted.toString())
                        InfoRow("❌ Unattempted", unattempted.toString())
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = viewModel::submitQuiz,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Submit Quiz",
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = viewModel::closeSubmitDialog,
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Review Again")
            }
        }
    )
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
