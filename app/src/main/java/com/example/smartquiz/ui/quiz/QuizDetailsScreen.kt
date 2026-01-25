package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartquiz.viewmodel.quiz.QuizDetailsViewModel

/**
 * QuizDetailsScreen
 *
 * Entry point of quiz flow.
 *
 * Responsibilities:
 * - Show quiz metadata
 * - Allow user to start quiz
 * - React to attemptId change for navigation
 *
 * This screen does NOT:
 * - Load questions
 * - Calculate score
 * - Handle answers
 */
@Composable
fun QuizDetailsScreen(
    quizId: String,
    userId: String,
    navController: NavController,
    viewModel: QuizDetailsViewModel = hiltViewModel()
) {

    // Load quiz data once when screen opens or quizId changes
    LaunchedEffect(quizId) {
        viewModel.loadQuizDetails(quizId)
    }

    // Observe ViewModel state
    val quiz by viewModel.quiz.collectAsState()
    val questionCount by viewModel.questionCount.collectAsState()
    val attemptId by viewModel.attemptId.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // Navigation trigger
    LaunchedEffect(attemptId) {
        attemptId?.let {
            navController.navigate("quiz_play/$quizId/$it")
        }
    }

    Box(modifier = Modifier
            .fillMaxSize()
    ) {
        // 🔄 Loading state
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            return
        }


        // ❌ Error state
        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
            return
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Quiz title
            Text(
                text = quiz?.title ?: "Quiz",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quiz category
            Text(
                text = "Category: ${quiz?.category ?: "-"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Question count
            Text(
                text = "$questionCount Questions",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Start quiz
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

        // Navigate ONLY after attempt is created
        LaunchedEffect(attemptId) {
            attemptId?.let {
                navController.navigate("quiz_play/$quizId/$it")
            }
        }
    }
}