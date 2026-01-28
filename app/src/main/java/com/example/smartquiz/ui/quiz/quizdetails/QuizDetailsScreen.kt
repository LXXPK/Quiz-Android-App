package com.example.smartquiz.ui.quiz.quizdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            navController.navigate(Routes.quizPlay(quizId, it))
            viewModel.consumeAttemptId()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                QuizDetailsContent(
                    quiz = quiz,
                    questionCount = questionCount,
                    onStartQuiz = {
                        viewModel.onStartQuizClicked(
                            quizId = quizId,
                            userId = userId
                        )
                    }
                )
            }
        }
    }
}
