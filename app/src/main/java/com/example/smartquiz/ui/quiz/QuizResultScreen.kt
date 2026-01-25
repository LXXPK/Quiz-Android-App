package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

/**
 * QuizResultScreen
 *
 * Shows final result after submission.
 *
 * This screen:
 * - Reads final state from ViewModel
 * - Displays score, accuracy and time
 *
 * This screen does NOT:
 * - Calculate results
 * - Access DB
 */
@Composable
fun QuizResultScreen(
    onDone: () -> Unit,
    viewModel: QuizPlayViewModel = hiltViewModel()
) {

    val score by viewModel.score.collectAsState()
    val correct by viewModel.correctCount.collectAsState()
    val percentage by viewModel.percentage.collectAsState()
    val totalQuestions by remember {
        derivedStateOf { viewModel.questions.value.size }
    }
    val elapsedTime by viewModel.elapsedTimeText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Quiz Completed 🎉",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Score: $score", style = MaterialTheme.typography.bodyLarge)
        Text("Correct: $correct / $totalQuestions")
        Text("Accuracy: $percentage%")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Time Spent: $elapsedTime")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDone
        ) {
            Text("Done")
        }
    }
}