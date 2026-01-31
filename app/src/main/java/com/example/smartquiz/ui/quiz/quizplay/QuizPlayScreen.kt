package com.example.smartquiz.ui.quiz.quizplay

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizPlayScreen(
    quizId: String,
    attemptId: Int,
    onFinish: () -> Unit,
    viewModel: QuizPlayViewModel = hiltViewModel()
) {

    BackHandler(enabled = true) {}

    LaunchedEffect(Unit) {
        viewModel.loadQuiz(quizId, attemptId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val isFinished by viewModel.isQuizFinished.collectAsState()

    LaunchedEffect(isFinished) {
        if (isFinished) onFinish()
    }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    QuizPlayContent(viewModel = viewModel)
}
