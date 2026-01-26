package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.viewmodel.quiz.QuizDetailsViewModel

@Composable
fun QuizDetailsScreen(
    quizId: String,
    userId: String,
    navController: NavController,
    viewModel: QuizDetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(quizId) {
        viewModel.loadQuizDetails(quizId)
    }

    val quiz by viewModel.quiz.collectAsState()
    val questionCount by viewModel.questionCount.collectAsState()
    val attemptId by viewModel.attemptId.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(attemptId) {
        attemptId?.let {
            navController.navigate(
                Routes.quizPlay(quizId, it)
            )
            viewModel.consumeAttemptId()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = quiz?.title ?: "",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text("Category: ${quiz?.category}")
                    Text("$questionCount Questions")

                    Spacer(modifier = Modifier.height(24.dp))

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
            }
        }
    }
}
