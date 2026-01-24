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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartquiz.viewmodel.quiz.QuizDetailsViewModel

/*
 * QuizDetailsScreen is the entry point of the quiz flow.
 *
 * Responsibilities:
 * - Show basic quiz information
 * - Let user start the quiz
 * - React to attempt creation (attemptId)
 *
 * This screen does NOT:
 * - Load questions
 * - Handle answers
 * - Calculate score
 */
@Composable
fun QuizDetailsScreen(
    quizId: String,
    userId: String,
    viewModel: QuizDetailsViewModel,
    navController: NavController
) {

    /*
     * Load quiz metadata and question count
     * when this screen is first shown.
     *
     * Tied to quizId so it reloads if quiz changes.
     */
    LaunchedEffect(quizId) {
        viewModel.loadQuizDetails(quizId)
    }

    // Observe state from ViewModel
    val quiz by viewModel.quiz.collectAsState()
    val questionCount by viewModel.questionCount.collectAsState()
    val attemptId by viewModel.attemptId.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        /*
         * Quiz title.
         * In dummy mode this will show "Dummy Quiz".
         */
        Text(
            text = quiz?.title ?: "Quiz",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        /*
         * Quiz category.
         * Optional metadata – safe to show even in dummy mode.
         */
        Text(
            text = "Category: ${quiz?.category ?: "-"}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        /*
         * Total number of questions.
         * Display-only information.
         */
        Text(
            text = "$questionCount Questions",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        /*
         * Start Quiz button.
         *
         * Clicking this:
         * - Creates a quiz attempt
         * - attemptId will be emitted by ViewModel
         */
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onStartQuizClicked(
                    quizId = quizId,
                    userId = userId
                )
            }
        ) {
            Text("Start Quiz")
        }
    }

    /*
     * Navigation trigger.
     *
     * We listen to attemptId changes instead of navigating
     * directly on button click.
     *
     * Why?
     * - Ensures attempt is actually created
     * - Keeps navigation reactive and safe
     */
    LaunchedEffect(attemptId) {
        if (attemptId != null) {
            navController.navigate(
                "quiz_play/$quizId/$attemptId"
            )
        }
    }
}