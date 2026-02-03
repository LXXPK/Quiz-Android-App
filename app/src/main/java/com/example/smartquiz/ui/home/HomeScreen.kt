package com.example.smartquiz.ui.home

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.viewmodel.home.HomeViewModel


@Composable
fun HomeScreen(
    handleQuizCardClick: (QuizEntity) -> Unit,
    onCategoryViewAll: (String) -> Unit,
    onSuggestedViewAll: () -> Unit,
    onHistoryClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadHome()
    }

    when {
        uiState.isLoading -> LoadingScreen()
        uiState.errorMessage != null ->
            ErrorScreen(message = uiState.errorMessage!!)
        else ->
            HomeScreenContent(
                homeViewModel = homeViewModel,
                handleQuizCardClick = handleQuizCardClick,
                onCategoryViewAll = onCategoryViewAll,
                onSuggestedViewAll = onSuggestedViewAll,
                onHistoryClick = onHistoryClick,
                uiState = uiState
            )
    }
}
