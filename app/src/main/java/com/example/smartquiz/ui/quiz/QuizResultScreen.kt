package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizResultScreen(
    onDone: () -> Unit,
    viewModel: QuizPlayViewModel
) {
    val score by viewModel.score.collectAsState()
    val correct by viewModel.correctCount.collectAsState()
    val percentage by viewModel.percentage.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val elapsedTime by viewModel.elapsedTimeText.collectAsState()

    val totalQuestions = questions.size

    if (totalQuestions == 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Quiz Completed",
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
