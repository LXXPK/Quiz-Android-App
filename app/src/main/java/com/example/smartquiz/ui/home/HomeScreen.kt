package com.example.smartquiz.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), handleQuizCardClick: (QuizEntity) -> Unit) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    if (homeUiState.isLoading) {
        LoadingScreen()
    } else if (homeUiState.errorMessage != null) {
        ErrorScreen(message = homeUiState.errorMessage!!)
    } else {
        HomeScreenContent(handleQuizCardClick = handleQuizCardClick, uiState = homeUiState)
    }
}
