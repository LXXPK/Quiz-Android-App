package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

/*
 * QuizResultScreen shows the final outcome of a quiz attempt.
 *
 * Responsibilities:
 * - Display score
 * - Display correct answers count
 * - Display percentage
 *
 * This screen does NOT:
 * - Recalculate score
 * - Fetch data from DB
 * - Decide correctness
 */
@Composable
fun QuizResultScreen(
    viewModel: QuizPlayViewModel,
    onDone: () -> Unit
) {
    // Collect result-related state from ViewModel
    val score by viewModel.score.collectAsState()
    val percentage by viewModel.percentage.collectAsState()
    val correct by viewModel.correctCount.collectAsState()
    val totalQuestions =
        viewModel.questions.collectAsState().value.size
    val elapsedTimeText by viewModel.elapsedTimeText.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        /*
         * Result header.
         * Keeps user oriented that quiz is finished.
         */
        Text(
            text = "Quiz Completed 🎉",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        /*
         * Score is a stored fact (QuizAttemptEntity).
         */
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.bodyLarge
        )

        /*
         * Correct answers count.
         * Derived information (not stored in DB).
         */
        Text(
            text = "Correct: $correct / $totalQuestions",
            style = MaterialTheme.typography.bodyMedium
        )

        /*
         * Accuracy percentage.
         * Derived from correct answers.
         */
        Text(
            text = "Accuracy: $percentage%",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Time spent
        Text(
            text = "Time Spent: $elapsedTimeText",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        /*
         * Done button.
         *
         * Navigation decision is delegated to caller.
         * This keeps UI reusable and decoupled.
         */
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDone
        ) {
            Text("Done")
        }
    }
}
