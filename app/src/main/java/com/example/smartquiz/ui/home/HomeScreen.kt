

package com.example.smartquiz.ui.home

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun HomeScreen(
    handleQuizCardClick: (QuizEntity) -> Unit,
    onCategoryViewAll: (String) -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
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
                onHistoryClick = onHistoryClick,
                onProfileClick = onProfileClick,
                uiState = uiState
            )
    }
}
