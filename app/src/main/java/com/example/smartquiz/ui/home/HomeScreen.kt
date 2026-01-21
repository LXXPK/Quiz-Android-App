package com.example.smartquiz.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    if (homeUiState.isLoading) {
        LoadingScreen()
    } else if (homeUiState.errorMessage != null) {
        ErrorScreen()
    } else {
        QuizListScreen(quizzes = homeUiState.quizzes)
    }
}

@Composable
fun QuizListScreen(quizzes: List<QuizEntity>, modifier: Modifier = Modifier) {
    // TODO: Create quizzes
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    // TODO: add spinner in center
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    // TODO: add error message
}